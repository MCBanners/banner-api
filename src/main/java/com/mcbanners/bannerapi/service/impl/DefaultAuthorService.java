package com.mcbanners.bannerapi.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mcbanners.bannerapi.net.CurseForgeClient;
import com.mcbanners.bannerapi.net.BuiltByBitClient;
import com.mcbanners.bannerapi.net.ModrinthClient;
import com.mcbanners.bannerapi.net.OreClient;
import com.mcbanners.bannerapi.net.PolyMartClient;
import com.mcbanners.bannerapi.net.SpigotClient;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeAuthor;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeProject;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitAuthor;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResourceBasic;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResourceBasicData;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthResource;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthUser;
import com.mcbanners.bannerapi.obj.backend.ore.OreAuthor;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.obj.backend.polymart.PolyMartAuthor;
import com.mcbanners.bannerapi.obj.backend.polymart.PolyMartAuthorStatistics;
import com.mcbanners.bannerapi.obj.backend.polymart.PolyMartAuthorUserData;
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
    private final PolyMartClient polyMartClient;
    private final BuiltByBitClient builtByBitClient;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public DefaultAuthorService(SpigotClient spigotClient, OreClient oreClient, CurseForgeClient curseForgeClient, ModrinthClient modrinthClient, PolyMartClient polyMartClient, BuiltByBitClient builtByBitClient) {
        this.spigotClient = spigotClient;
        this.oreClient = oreClient;
        this.curseForgeClient = curseForgeClient;
        this.modrinthClient = modrinthClient;
        this.polyMartClient = polyMartClient;
        this.builtByBitClient = builtByBitClient;
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
            case POLYMART:
                return handlePolyMart(authorId);
            case BUILTBYBIT:
                return handleBuiltByBit(authorId);
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
            case POLYMART:
            case BUILTBYBIT:
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
            totalReviews += Integer.parseInt(resource.getStats().getReviews().getTotal());
        }

        final String rawIcon = author.getAvatar();
        final String[] iconSplit = rawIcon.split("\\?");

        String spigotAuthorIcon = loadSpigotAuthorIcon(iconSplit[0]);
        if (spigotAuthorIcon == null) {
            spigotAuthorIcon = "";
        }

        return new Author(
                author.getUsername(),
                Integer.parseInt(author.getResourceCount()),
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
            return null;
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
    private Author handleModrinth(String authorName) {
        ModrinthUser author = loadModrinthUser(authorName);

        if (author == null) {
            return null;
        }

        ModrinthResource[] projects = loadAllModrinthResourcesByAuthor(authorName);

        if (projects == null) {
            return null;
        }

        int totalDownloads = 0, totalFollowers = 0;

        for (ModrinthResource project : projects) {
            totalDownloads += project.getDownloads();
            totalFollowers += project.getFollowers();
        }

        String modrinthAuthorAvatar = loadModrinthAuthorIcon(author.getAvatarUrl());
        if (modrinthAuthorAvatar == null) {
            modrinthAuthorAvatar = "";
        }

        return new Author(
                author.getName(),
                projects.length,
                modrinthAuthorAvatar,
                totalDownloads,
                totalFollowers,
                -1
        );
    }

    private String loadModrinthAuthorIcon(String url) {
        ResponseEntity<byte[]> resp = modrinthClient.getResourceIcon(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }

    private ModrinthUser loadModrinthUser(String username) {
        ResponseEntity<ModrinthUser> resp = modrinthClient.getUserInformation(username);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private ModrinthResource[] loadAllModrinthResourcesByAuthor(String username) {
        ResponseEntity<ArrayNode> resp = modrinthClient.getUserProjects(username);
        if (resp == null) {
            return null;
        }

        ArrayNode projects = resp.getBody();
        return mapper.convertValue(projects, ModrinthResource[].class);
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

    // BuiltByBit Handling
    private Author handleBuiltByBit(int authorId) {
        BuiltByBitAuthor author = loadBuiltByBitAuthor(authorId);

        if (author == null || author.getResult().equals("error")) {
            return null;
        }

        BuiltByBitResourceBasic resources = loadBuiltByBitAuthorBasic(authorId);

        if (resources == null || resources.getData().length == 0) {
            return null;
        }

        int totalDownloads = 0, totalReviews = 0;

        for (final BuiltByBitResourceBasicData resource : resources.getData()) {
            totalDownloads += resource.getDownloadCount();
            totalReviews += resource.getReviewCount();
        }

        String avatarUrl = author.getData().getAvatarUrl();
        avatarUrl = avatarUrl.replace("https://static.mc-market.org", "https://cdn.builtbybit.com");

        String builtByBitAuthorIcon = loadBuiltByBitAuthorIcon(avatarUrl);
        if (builtByBitAuthorIcon == null) {
            builtByBitAuthorIcon = "";
        }

        return new Author(
                author.getData().getUsername(),
                author.getData().getResourceCount(),
                builtByBitAuthorIcon,
                totalDownloads,
                -1,
                totalReviews
        );
    }

    private BuiltByBitResourceBasic loadBuiltByBitAuthorBasic(int authorId) {
        ResponseEntity<BuiltByBitResourceBasic> resp = builtByBitClient.getAllByAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private BuiltByBitAuthor loadBuiltByBitAuthor(int authorId) {
        ResponseEntity<BuiltByBitAuthor> resp = builtByBitClient.getAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadBuiltByBitAuthorIcon(String url) {
        ResponseEntity<byte[]> resp = builtByBitClient.getIcon(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }

    // PolyMart Handling
    private Author handlePolyMart(final int authorId) {
        final PolyMartAuthor author = loadPolyMartAuthor(authorId);

        if (author == null) {
            return null;
        }

        final PolyMartAuthorUserData data = author.getResponse().getUser();
        final PolyMartAuthorStatistics statistics = data.getStatistics();
        final String authorImage = loadPolyMartImage(data.getProfilePictureURL());

        return new Author(
                data.getUsername(),
                statistics.getResourceCount(),
                authorImage,
                statistics.getResourceDownloads(),
                -1,
                statistics.getResourceRatings()
        );
    }

    private PolyMartAuthor loadPolyMartAuthor(final int authorId) {
        final ResponseEntity<PolyMartAuthor> resp = polyMartClient.getAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadPolyMartImage(final String url) {
        final ResponseEntity<byte[]> resp = polyMartClient.getIcon(url);
        if (resp == null) {
            return null;
        }

        final byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }
}
