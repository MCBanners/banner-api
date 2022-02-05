package com.mcbanners.bannerapi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.mcbanners.bannerapi.net.CurseForgeClient;
import com.mcbanners.bannerapi.net.ModrinthClient;
import com.mcbanners.bannerapi.net.OreClient;
import com.mcbanners.bannerapi.net.SpigotClient;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeAuthor;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeProject;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthTeamUser;
import com.mcbanners.bannerapi.obj.backend.ore.OreAuthor;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotAuthor;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotResource;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.api.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"author"})
public class DefaultAuthorService implements AuthorService {
    private final SpigotClient spigotClient;
    private final OreClient oreClient;
    private final CurseForgeClient curseForgeClient;
    private final ModrinthClient modrinthClient;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public DefaultAuthorService(SpigotClient spigotClient, OreClient oreClient, CurseForgeClient curseForgeClient, ModrinthClient modrinthClient) {
        this.spigotClient = spigotClient;
        this.oreClient = oreClient;
        this.curseForgeClient = curseForgeClient;
        this.modrinthClient = modrinthClient;
    }

    /**
     * Get an author by its id on the specified service backend.
     *
     * @param authorId the author ID
     * @param backend  the service backend to query
     * @return the Author object or null if the service backend does not support the operation or the author could not be found.
     */
    @Override
    @Cacheable(unless = "#result == null")
    public Author getAuthor(int authorId, ServiceBackend backend) {
        switch (backend) {
            case SPIGOT:
                return handleSpigot(authorId);
            case CURSEFORGE:
                return handleCurseForge(authorId, null);
            case ORE:
            default:
                return null;
        }
    }

    /**
     * Get an author by its name on the specified service backend.
     *
     * @param authorName the author name
     * @param backend    the service backend to query
     * @return the Author object or null if the service bannerapi does not support the operation or the author could not be found.
     */
    @Override
    @Cacheable(unless = "#result == null")
    public Author getAuthor(String authorName, ServiceBackend backend) {
        switch (backend) {
            case ORE:
                return handleOre(authorName);
            case CURSEFORGE:
                return handleCurseForge(0, authorName);
            case MODRINTH:
                return handleModrinth(authorName);
            case SPIGOT:
            default:
                return null;
        }
    }

    // Spigot handling
    private Author handleSpigot(int authorId) {
        SpigotAuthor author = loadSpigotAuthor(authorId);
        SpigotResource[] resources = loadAllSpigotResourcesByAuthor(authorId);

        if (author == null || resources == null) {
            return null;
        }

        int totalDownloads = 0, totalReviews = 0;

        for (SpigotResource resource : resources) {
            totalDownloads += Integer.parseInt(resource.getStats().getDownloads());
            totalReviews += Integer.parseInt(resource.getStats().getReviews());
        }

        String hash = author.getAvatar().getHash();
        String info = author.getAvatar().getInfo();

        String authorAvatarUrl = "";

        if (hash != null && !hash.isEmpty()) {
            authorAvatarUrl = String.format("http://gravatar.com/avatar/%s.jpg?s=96", author.getAvatar().getHash());
        } else if (info != null && !info.isEmpty()) {
            int imageFolder = authorId / 1000;
            authorAvatarUrl = String.format("https://www.spigotmc.org/data/avatars/l/%d/%d.jpg?%s", imageFolder, authorId, info);
        }

        String spigotAuthorIcon = loadSpigotAuthorIcon(authorAvatarUrl);
        if (spigotAuthorIcon == null) {
            spigotAuthorIcon = "";
        }


        return new Author(
                author.getUsername(),
                Integer.parseInt(author.getResource_count()),
                spigotAuthorIcon,
                totalDownloads,
                -1,
                totalReviews
        );
    }

    private SpigotAuthor loadSpigotAuthor(int authorId) {
        ResponseEntity<SpigotAuthor> resp = spigotClient.getAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private SpigotResource[] loadAllSpigotResourcesByAuthor(int authorId) {
        ResponseEntity<SpigotResource[]> resp = spigotClient.getAllByAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadSpigotAuthorIcon(String url) {
        ResponseEntity<byte[]> resp = spigotClient.getResourceIcon(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }

    // Ore handling
    private Author handleOre(String authorName) {
        OreResource[] resources = loadOreAuthorProjects(authorName);

        if (resources == null) {
            return null;
        }

        int totalDownloads = 0, totalLikes = 0;

        for (OreResource resource : resources) {
            totalDownloads += resource.getStats().getDownloads();
            totalLikes += resource.getStats().getStars();
        }

        String oreAuthorAvatar = loadOreImageByUrl(authorName);
        if (oreAuthorAvatar == null) {
            oreAuthorAvatar = "";
        }

        return new Author(
                resources[0].getNamespace().getOwner(),
                resources.length,
                oreAuthorAvatar,
                totalDownloads,
                totalLikes,
                -1 // unknown
        );
    }

    private OreAuthor loadOreAuthor(String authorId) {
        ResponseEntity<OreAuthor> resp = oreClient.getAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private OreResource[] loadOreAuthorProjects(String authorId) {
        ResponseEntity<JsonNode> resp = oreClient.getProjectsFromAuthor(authorId);
        if (resp == null) {
            return null;
        }

        JsonNode data = resp.getBody().get("result");
        if (!data.isArray()) {
            return  null;
        }

        return mapper.convertValue(data, OreResource[].class);
    }

    private String loadOreImageByUrl(String url) {
        ResponseEntity<byte[]> resp = oreClient.getAuthorIcon(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }

    // Modrinth Handling
    private Author handleModrinth(String teamName) {
        ArrayNode modrinthTeam = loadModrinthTeam(teamName);

        if (modrinthTeam == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        ModrinthTeamUser author = mapper.convertValue(modrinthTeam.get(0).get("user"), ModrinthTeamUser.class);

        return new Author(
                author.getName(),
                0,
                "",
                -1,
                -1,
                -1
        );
    }

    private ArrayNode loadModrinthTeam(String id) {
        ResponseEntity<ArrayNode> resp = modrinthClient.getAuthor(id);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    // Curse handling
    private Author handleCurseForge(int authorId, String authorName) {
        CurseForgeAuthor author;

        if (authorId != 0) {
            author = loadCurseForgeAuthor(authorId);
        } else {
            author = loadCurseForgeAuthor(authorName);
        }

        if (author == null) {
            return null;
        }

        List<CurseForgeResource> resources = loadAllCurseForgeResourcesByAuthor(author);

        int totalDownloads = 0;

        for (CurseForgeResource resource : resources) {
            totalDownloads += resource.getDownloads().getTotal();
        }

        return new Author(
                author.getUsername(),
                author.getProjects().size(),
                "",
                totalDownloads,
                -1,
                -1
        );
    }

    private CurseForgeAuthor loadCurseForgeAuthor(int authorId) {
        ResponseEntity<CurseForgeAuthor> resp = curseForgeClient.getAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private CurseForgeAuthor loadCurseForgeAuthor(String authorName) {
        ResponseEntity<CurseForgeAuthor> resp = curseForgeClient.getAuthor(authorName);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private List<CurseForgeResource> loadAllCurseForgeResourcesByAuthor(CurseForgeAuthor author) {
        List<CurseForgeResource> resources = new ArrayList<>();

        for (CurseForgeProject project : author.getProjects()) {
            ResponseEntity<CurseForgeResource> resp = curseForgeClient.getResource(project.getId());
            if (resp != null) {
                resources.add(resp.getBody());
            }
        }
        return resources;
    }
}
