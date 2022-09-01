package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.BuiltByBitClient;
import com.mcbanners.bannerapi.net.CurseForgeClient;
import com.mcbanners.bannerapi.net.ModrinthClient;
import com.mcbanners.bannerapi.net.OreClient;
import com.mcbanners.bannerapi.net.PolymartClient;
import com.mcbanners.bannerapi.net.SpigotClient;
import com.mcbanners.bannerapi.net.error.FurtherProcessingRequiredException;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitAuthor;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResource;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthResource;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthUser;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartResource;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotPremium;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotResource;
import com.mcbanners.bannerapi.obj.generic.PriceInformation;
import com.mcbanners.bannerapi.obj.generic.RatingInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.api.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Locale;

@Service
@CacheConfig(cacheNames = {"resource"})
public class DefaultResourceService implements ResourceService {
    private final SpigotClient spigotClient;
    private final OreClient oreClient;
    private final CurseForgeClient curseForgeClient;
    private final ModrinthClient modrinthClient;
    private final BuiltByBitClient builtByBitClient;
    private final PolymartClient polymartClient;

    @Autowired
    public DefaultResourceService(SpigotClient spigotClient, OreClient oreClient, CurseForgeClient curseForgeClient, ModrinthClient modrinthClient, BuiltByBitClient builtByBitClient, PolymartClient polymartClient) {
        this.spigotClient = spigotClient;
        this.oreClient = oreClient;
        this.curseForgeClient = curseForgeClient;
        this.modrinthClient = modrinthClient;
        this.builtByBitClient = builtByBitClient;
        this.polymartClient = polymartClient;
    }


    /**
     * Get a resource by its ID on the specified service backend.
     *
     * @param resourceId the resource ID
     * @param backend    the service backend to query
     * @return the Resource object or null if the service backend does not support the operation or the resource could not be found.
     */
    @Override
    @Cacheable(unless = "#result == null")
    public Resource getResource(int resourceId, ServiceBackend backend) throws FurtherProcessingRequiredException {
        switch (backend) {
            case SPIGOT:
                return handleSpigot(resourceId);
            case CURSEFORGE:
                return handleCurse(resourceId);
            case BUILTBYBIT:
                return handleBuiltByBit(resourceId);
            case POLYMART:
                return handlePolymart(resourceId);
            case ORE:
            default:
                return null;
        }
    }

    /**
     * Get a resource by its name on the specified service backend.
     *
     * @param pluginId the resource name
     * @param backend  the service backend to query
     * @return the Resource object or null if the service backend does not support the operation or the resource could not be found.
     */
    @Override
    @Cacheable(unless = "#result == null")
    public Resource getResource(String pluginId, ServiceBackend backend) {
        switch (backend) {
            case ORE:
                return handleOre(pluginId);
            case MODRINTH:
                return handleModrinth(pluginId);
            case CURSEFORGE:
            case SPIGOT:
            case POLYMART:
            case BUILTBYBIT:
            default:
                return null;
        }
    }

    // Spigot handling
    private Resource handleSpigot(int resourceId) {
        SpigotResource spigotResource = loadSpigotResource(resourceId);

        if (spigotResource == null) {
            return null;
        }

        final String rawIcon = spigotResource.getIconLink();
        final String[] iconSplit = rawIcon.split("\\?");

        String spigotResourceIcon = loadSpigotResourceIcon(iconSplit[0]);
        if (spigotResourceIcon == null) {
            spigotResourceIcon = "";
        }

        SpigotPremium premium = spigotResource.getPremium();
        boolean isPremium = !premium.getPrice().equals("0.00");

        return new Resource(
                spigotResourceIcon,
                spigotResource.getTitle(),
                Integer.parseInt(spigotResource.getAuthor().getId()),
                spigotResource.getAuthor().getUsername(),
                new RatingInformation(
                        Integer.parseInt(spigotResource.getStats().getReviews().getUnique()),
                        Double.parseDouble(spigotResource.getStats().getRating())
                ),
                Integer.parseInt(spigotResource.getStats().getDownloads()),
                isPremium ? new PriceInformation(Double.parseDouble(premium.getPrice()), premium.getCurrency().toUpperCase()) : null,
                null);
    }

    private SpigotResource loadSpigotResource(int resourceId) {
        ResponseEntity<SpigotResource> resp = spigotClient.getResource(resourceId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadSpigotResourceIcon(String url) {
        ResponseEntity<byte[]> resp = spigotClient.getImage(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }

    // Ore handling
    private Resource handleOre(String pluginId) {
        OreResource oreResource = loadOreResource(pluginId);
        if (oreResource == null) {
            return null;
        }

        String oreResourceIcon = loadOreResourceIcon(oreResource.iconUrl());
        if (oreResourceIcon == null) {
            oreResourceIcon = "";
        }

        return new Resource(
                oreResourceIcon,
                oreResource.name(),
                -1, // not known
                oreResource.owner(), // username
                new RatingInformation(oreResource.stars()),
                oreResource.downloads(),
                null,
                null);
    }

    private OreResource loadOreResource(String pluginId) {
        ResponseEntity<OreResource> resp = oreClient.getResource(pluginId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadOreResourceIcon(String href) {
        ResponseEntity<byte[]> resp = oreClient.getImage(href);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }

    // Curse handling
    private Resource handleCurse(int resourceId) throws FurtherProcessingRequiredException {
        CurseForgeResource curseForgeResource = loadCurseForgeResource(resourceId);
        if (curseForgeResource == null) {
            return null;
        }

        CurseForgeResource.Member author = curseForgeResource.members().stream()
                .filter(member -> member.title().equalsIgnoreCase("Owner"))
                .findFirst().orElse(null);

        if (author == null) {
            return null;
        }

        String curseForgeResourceIcon = loadCurseForgeResourceIcon(curseForgeResource.thumbnail());

        return new Resource(
                curseForgeResourceIcon,
                curseForgeResource.title(),
                author.id(),
                author.username(),
                new RatingInformation(0, 0.0),
                curseForgeResource.totalDownloads(),
                null,
                curseForgeResource.uploadedAt()
        );
    }

    private CurseForgeResource loadCurseForgeResource(int resourceId) throws FurtherProcessingRequiredException {
        ResponseEntity<CurseForgeResource> resp = curseForgeClient.getResource(resourceId);
        if (resp == null) {
            return null;
        }

        if (resp.getStatusCodeValue() == 202) {
            throw new FurtherProcessingRequiredException(
                    "CurseForge is currently processing the requested resource and has asked us to wait while " +
                            "the processing completes. Please try your request again in about 30 seconds. Sorry " +
                            "for the inconvenience.");
        } else {
            return resp.getBody();
        }

    }

    private String loadCurseForgeResourceIcon(String url) {
        ResponseEntity<byte[]> resp = curseForgeClient.getImage(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }

    // Modrinth Handling
    private Resource handleModrinth(String pluginId) {
        ModrinthResource modrinthResource = loadModrinthResource(pluginId);
        if (modrinthResource == null) {
            return null;
        }

        String modrinthResourceIcon = loadModrinthResourceIcon(modrinthResource.iconUrl());
        if (modrinthResourceIcon == null) {
            modrinthResourceIcon = "";
        }

        ModrinthUser mainAuthor = loadModrinthMainProjectAuthor(pluginId);
        if (mainAuthor == null) {
            return null;
        }

        return new Resource(
                modrinthResourceIcon,
                modrinthResource.title(),
                -1,
                mainAuthor.username(),
                new RatingInformation(0, 0.0),
                modrinthResource.downloads(),
                null,
                modrinthResource.updated());
    }

    private ModrinthResource loadModrinthResource(String pluginId) {
        ResponseEntity<ModrinthResource> resp = modrinthClient.getResource(pluginId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private ModrinthUser loadModrinthMainProjectAuthor(String pluginId) {
        ResponseEntity<ModrinthUser> resp = modrinthClient.getMainProjectAuthor(pluginId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadModrinthResourceIcon(String url) {
        ResponseEntity<byte[]> resp = modrinthClient.getImage(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }

    // BuiltByBit handling
    private Resource handleBuiltByBit(int resourceId) {
        BuiltByBitResource resource = loadBuiltByBitResource(resourceId);

        if (resource == null) {
            return null;
        }

        BuiltByBitAuthor author = loadBuiltByBitAuthor(resource.authorId());

        if (author == null) {
            return null;
        }

        boolean isPremium = resource.price() != 0.0;

        int downloadsToShow;

        if (isPremium) {
            downloadsToShow = resource.purchaseCount();
        } else {
            downloadsToShow = resource.downloadCount();
        }

        return new Resource(
                "",
                resource.title(),
                author.authorId(),
                author.username(),
                new RatingInformation(
                        resource.reviewCount(),
                        resource.reviewAverage()
                ),
                downloadsToShow,
                isPremium ? new PriceInformation(resource.price(), resource.currency().toUpperCase()) : null,
                null
        );
    }

    private BuiltByBitResource loadBuiltByBitResource(int resourceId) {
        ResponseEntity<BuiltByBitResource> resp = builtByBitClient.getResource(resourceId);
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

    // Polymart handling
    private Resource handlePolymart(final int resourceId) {
        final PolymartResource resource = loadPolymartResource(resourceId);
        if (resource == null) {
            return null;
        }

        final String image = loadPolymartImage(resource.thumbnailURL());
        final boolean isPremium = !(resource.price() == 0.00);

        return new Resource(
                image,
                resource.title(),
                resource.ownerId(),
                resource.ownerName(),
                new RatingInformation(
                        resource.reviewCount(),
                        (double) resource.stars()
                ),
                resource.downloads(),
                isPremium ? new PriceInformation(resource.price(), resource.currency().toUpperCase(Locale.ROOT)) : null,
                null
        );
    }

    private PolymartResource loadPolymartResource(final int resourceId) {
        final ResponseEntity<PolymartResource> resp = polymartClient.getResource(resourceId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadPolymartImage(final String url) {
        final ResponseEntity<byte[]> resp = polymartClient.getImage(url);
        if (resp == null) {
            return null;
        }

        final byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }
}
