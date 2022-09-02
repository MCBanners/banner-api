package com.mcbanners.bannerapi.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcbanners.bannerapi.net.BuiltByBitClient;
import com.mcbanners.bannerapi.net.CurseForgeClient;
import com.mcbanners.bannerapi.net.ModrinthClient;
import com.mcbanners.bannerapi.net.OreClient;
import com.mcbanners.bannerapi.net.PolymartClient;
import com.mcbanners.bannerapi.net.SpigotClient;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitAuthor;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResource;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResourceBasic;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeAuthor;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthResource;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthUser;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartAuthor;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartResource;
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
import java.util.List;

@Service
@CacheConfig(cacheNames = {"author"})
public class DefaultAuthorService implements AuthorService {
    private final SpigotClient spigotClient;
    private final OreClient oreClient;
    private final CurseForgeClient curseForgeClient;
    private final ModrinthClient modrinthClient;
    private final PolymartClient polymartClient;
    private final BuiltByBitClient builtByBitClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public DefaultAuthorService(SpigotClient spigotClient, OreClient oreClient, CurseForgeClient curseForgeClient, ModrinthClient modrinthClient, PolymartClient polymartClient, BuiltByBitClient builtByBitClient) {
        this.spigotClient = spigotClient;
        this.oreClient = oreClient;
        this.curseForgeClient = curseForgeClient;
        this.modrinthClient = modrinthClient;
        this.polymartClient = polymartClient;
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
            case BUILTBYBIT:
                return handleBuiltByBit(authorId);
            case POLYMART:
                return handlePolymart(authorId);
            case ORE:
            default:
                return null;
        }
    }

    @Override
    @Cacheable(unless = "#result == null")
    public Author getAuthor(int authorId, int resourceId, ServiceBackend backend) {
        return handlePolymart(authorId, resourceId);
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
            totalDownloads += Integer.parseInt(resource.downloads());
            totalReviews += Integer.parseInt(resource.totalReviews());
        }

        final String rawIcon = author.avatar();
        final String[] iconSplit = rawIcon.split("\\?");

        String spigotAuthorIcon = spigotClient.getBase64Image(iconSplit[0]);

        if (spigotAuthorIcon == null) {
            spigotAuthorIcon = "";
        }

        return new Author(
                author.username(),
                Integer.parseInt(author.resourceCount()),
                spigotAuthorIcon,
                totalDownloads,
                -1,
                totalReviews
        );
    }

    private SpigotAuthor loadSpigotAuthor(int authorId) {
        final ResponseEntity<SpigotAuthor> resp = spigotClient.getAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }

    private SpigotResource[] loadAllSpigotResourcesByAuthor(int authorId) {
        final ResponseEntity<SpigotResource[]> resp = spigotClient.getAllByAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }

    // Ore handling
    private Author handleOre(String authorName) {
        OreResource[] resources = loadOreAuthorProjects(authorName);
        if (resources == null) {
            return null;
        }

        // TODO: what if they have more than 25 projects
        int totalDownloads = 0, totalLikes = 0;
        for (OreResource resource : resources) {
            totalDownloads += resource.downloads();
            totalLikes += resource.stars();
        }

        // Todo figure out why this is weird
        String oreAuthorAvatar = oreClient.getBase64Image(authorName);
        if (oreAuthorAvatar == null) {
            oreAuthorAvatar = "";
        }

        // TODO: what if they have no projects (resources[0]?)
        return new Author(
                resources[0].owner(),
                resources.length,
                oreAuthorAvatar,
                totalDownloads,
                totalLikes,
                -1 // unknown
        );
    }

    private OreResource[] loadOreAuthorProjects(String authorId) {
        ResponseEntity<JsonNode> resp = oreClient.getProjectsFromAuthor(authorId);
        if (resp == null) {
            return null;
        }

        JsonNode data = resp.getBody();
        if (data == null || (!data.has("result") || !data.get("result").isArray())) {
            return null;
        }

        return mapper.convertValue(data.get("result"), OreResource[].class);
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
            totalDownloads += project.downloads();
            totalFollowers += project.followers();
        }

        String modrinthAuthorAvatar = modrinthClient.getBase64Image(author.avatarUrl());
        if (modrinthAuthorAvatar == null) {
            modrinthAuthorAvatar = "";
        }

        return new Author(
                author.username(),
                projects.length,
                modrinthAuthorAvatar,
                totalDownloads,
                totalFollowers,
                -1
        );
    }

    private ModrinthUser loadModrinthUser(String username) {
        final ResponseEntity<ModrinthUser> resp = modrinthClient.getUserInformation(username);
        return resp == null ? null : resp.getBody();
    }

    private ModrinthResource[] loadAllModrinthResourcesByAuthor(String username) {
        final ResponseEntity<ModrinthResource[]> resp = modrinthClient.getUserProjects(username);
        return resp == null ? null : resp.getBody();
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
            totalDownloads += resource.totalDownloads();
        }

        return new Author(
                author.username(),
                author.projects().size(),
                "",
                totalDownloads,
                -1,
                -1
        );
    }

    private CurseForgeAuthor loadCurseForgeAuthor(int authorId) {
        final ResponseEntity<CurseForgeAuthor> resp = curseForgeClient.getAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }

    private CurseForgeAuthor loadCurseForgeAuthor(String authorName) {
        final ResponseEntity<CurseForgeAuthor> resp = curseForgeClient.getAuthor(authorName);
        return resp == null ? null : resp.getBody();
    }

    private List<CurseForgeResource> loadAllCurseForgeResourcesByAuthor(CurseForgeAuthor author) {
        List<CurseForgeResource> resources = new ArrayList<>();

        for (CurseForgeAuthor.Project project : author.projects()) {
            ResponseEntity<CurseForgeResource> resp = curseForgeClient.getResource(project.id());
            if (resp != null) {
                resources.add(resp.getBody());
            }
        }
        return resources;
    }

    // BuiltByBit Handling
    private Author handleBuiltByBit(int authorId) {
        BuiltByBitAuthor author = loadBuiltByBitAuthor(authorId);

        if (author == null) {
            return null;
        }

        BuiltByBitResourceBasic resources = loadBuiltByBitAuthorBasic(authorId);

        if (resources == null || resources.resources().size() == 0) {
            return null;
        }

        int totalDownloads = 0, totalReviews = 0;

        for (final BuiltByBitResource resource : resources.resources()) {
            totalDownloads += resource.downloadCount();
            totalReviews += resource.reviewCount();
        }

        String avatarUrl = builtByBitClient.getBase64Image(author.avatarUrl());

        if (avatarUrl == null) {
            avatarUrl = "";
        }

        return new Author(
                author.username(),
                author.resourceCount(),
                avatarUrl,
                totalDownloads,
                -1,
                totalReviews
        );
    }

    private BuiltByBitResourceBasic loadBuiltByBitAuthorBasic(int authorId) {
        final ResponseEntity<BuiltByBitResourceBasic> resp = builtByBitClient.getAllByAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }

    private BuiltByBitAuthor loadBuiltByBitAuthor(int authorId) {
        final ResponseEntity<BuiltByBitAuthor> resp = builtByBitClient.getAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }

    // Regular Polymart Handling
    private Author handlePolymart(final int authorId) {
        final PolymartAuthor author = loadPolymartAuthor(authorId);
        if (author == null) {
            return null;
        }

        final String authorImage = polymartClient.getBase64Image(author.profilePictureURL());

        return new Author(
                author.username(),
                author.resourceCount(),
                authorImage,
                author.resourceDownloads(),
                -1,
                author.resourceRatings()
        );
    }

    // Major Polymart Workaround
    private Author handlePolymart(final int authorId, final int resourceId) {
        final PolymartResource resource = loadPolymartResource(resourceId);
        if (resource == null) {
            return null;
        }

        final PolymartAuthor author = resource.ownerType().equals("user")
                ? loadPolymartAuthor(authorId) : loadPolymartTeam(resource.ownerId());

        if (author == null) {
            return null;
        }

        String ownerImage = polymartClient.getBase64Image(author.profilePictureURL());

        return new Author(
                author.username(),
                author.resourceCount(),
                ownerImage,
                author.resourceDownloads(),
                -1,
                author.resourceRatings()
        );
    }

    private PolymartResource loadPolymartResource(final int resourceId) {
        final ResponseEntity<PolymartResource> resp = polymartClient.getResource(resourceId);
        return resp == null ? null : resp.getBody();
    }

    private PolymartAuthor loadPolymartAuthor(final int authorId) {
        final ResponseEntity<PolymartAuthor> resp = polymartClient.getAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }

    private PolymartAuthor loadPolymartTeam(final int teamId) {
        final ResponseEntity<PolymartAuthor> resp = polymartClient.getTeam(teamId);
        return resp == null ? null : resp.getBody();
    }
}
