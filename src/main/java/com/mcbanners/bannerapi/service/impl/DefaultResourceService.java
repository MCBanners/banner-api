package com.mcbanners.bannerapi.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mcbanners.bannerapi.net.CurseForgeClient;
import com.mcbanners.bannerapi.net.MCMarketClient;
import com.mcbanners.bannerapi.net.ModrinthClient;
import com.mcbanners.bannerapi.net.OreClient;
import com.mcbanners.bannerapi.net.PolyMartClient;
import com.mcbanners.bannerapi.net.SpigotClient;
import com.mcbanners.bannerapi.net.error.FurtherProcessingRequiredException;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResourceMember;
import com.mcbanners.bannerapi.obj.backend.mcmarket.MCMarketAuthor;
import com.mcbanners.bannerapi.obj.backend.mcmarket.MCMarketResource;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthResource;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.obj.backend.polymart.PolyMartResource;
import com.mcbanners.bannerapi.obj.backend.polymart.PolyMartResourceData;
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
    private final MCMarketClient mcMarketClient;
    private final PolyMartClient polyMartClient;

    @Autowired
    public DefaultResourceService(SpigotClient spigotClient, OreClient oreClient, CurseForgeClient curseForgeClient, ModrinthClient modrinthClient, PolyMartClient polyMartClient) {
    public DefaultResourceService(SpigotClient spigotClient, OreClient oreClient, CurseForgeClient curseForgeClient, ModrinthClient modrinthClient, MCMarketClient mcMarketClient) {
        this.spigotClient = spigotClient;
        this.oreClient = oreClient;
        this.curseForgeClient = curseForgeClient;
        this.modrinthClient = modrinthClient;
        this.polyMartClient = polyMartClient;
        this.mcMarketClient = mcMarketClient;
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
            case MCMARKET:
                return handleMcMarket(resourceId);
            case POLYMART:
                return handlePolyMart(resourceId);
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

        String rawURL = "https://www.spigotmc.org/data/resource_icons/%s/%s.jpg";
        int imageFolder = (int) Math.floor(Double.parseDouble(spigotResource.getId()) / 1000);
        String finalUrl = String.format(rawURL, imageFolder, spigotResource.getId());

        String spigotResourceIcon = loadSpigotResourceIcon(finalUrl);
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
                        Integer.parseInt(spigotResource.getStats().getReviews()),
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
        ResponseEntity<byte[]> resp = spigotClient.getResourceIcon(url);
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

        String oreResourceIcon = loadOreResourceIcon(oreResource.getIconUrl());
        if (oreResourceIcon == null) {
            oreResourceIcon = "";
        }

        return new Resource(
                oreResourceIcon,
                oreResource.getName(),
                -1, // not known
                oreResource.getNamespace().getOwner(), // username
                new RatingInformation(oreResource.getStats().getStars()),
                oreResource.getStats().getDownloads(),
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

        CurseForgeResourceMember author = curseForgeResource.getMembers().stream().filter(member -> member.getTitle().equalsIgnoreCase("Owner")).findFirst().orElse(null);
        if (author == null) {
            return null;
        }

        String curseForgeResourceIcon = loadCurseForgeResourceIcon(curseForgeResource.getThumbnail());

        return new Resource(
                curseForgeResourceIcon,
                curseForgeResource.getTitle(),
                author.getId(),
                author.getUsername(),
                new RatingInformation(0, 0.0),
                curseForgeResource.getDownloads().getTotal(),
                null,
                curseForgeResource.getDownload().getUploadedAt());
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
        ResponseEntity<byte[]> resp = curseForgeClient.getResourceIcon(url);
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

        String modrinthResourceIcon = loadModrinthResourceIcon(modrinthResource.getIconUrl());
        if (modrinthResourceIcon == null) {
            modrinthResourceIcon = "";
        }

        return new Resource(
                modrinthResourceIcon,
                modrinthResource.getTitle(),
                -1,
                determineModrinthResourceAuthor(pluginId),
                new RatingInformation(0, 0.0),
                modrinthResource.getDownloads(),
                null,
                modrinthResource.getUpdated());
    }

    private ModrinthResource loadModrinthResource(String pluginId) {
        ResponseEntity<ModrinthResource> resp = modrinthClient.getResource(pluginId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String determineModrinthResourceAuthor(String pluginId) {
        ResponseEntity<ArrayNode> resp = modrinthClient.getMainProjectAuthor(pluginId);
        if (resp == null) {
            return null;
        }

        ArrayNode team = resp.getBody();
        return team.get(0).get("user").get("username").textValue();
    }

    private String loadModrinthResourceIcon(String url) {
        ResponseEntity<byte[]> resp = modrinthClient.getResourceIcon(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }

    // MC-Market handling
    private Resource handleMcMarket(int resourceId) {
        MCMarketResource resource = loadMCMarketResource(resourceId);

        if (resource == null || resource.getResult().equals("error")) {
            return null;
        }

        MCMarketAuthor author = loadMCMarketAuthor(resource.getData().getAuthorId());

        if (author == null || author.getResult().equals("error")) {
            return null;
        }

        boolean isPremium = resource.getData().getPrice() != 0.0;

        int downloadsToShow;

        if (isPremium) {
            downloadsToShow = resource.getData().getPurchaseCount();
        } else {
            downloadsToShow = resource.getData().getDownloadCount();
        }

        return new Resource(
                "",
                resource.getData().getTitle(),
                author.getData().getMemberId(),
                author.getData().getUsername(),
                new RatingInformation(
                        resource.getData().getReviewCount(),
                        resource.getData().getReviewAverage()
                ),
                downloadsToShow,
                isPremium ? new PriceInformation(resource.getData().getPrice(), resource.getData().getCurrency().toUpperCase()) : null,
                null);
    }

    private MCMarketResource loadMCMarketResource(int resourceId) {
        ResponseEntity<MCMarketResource> resp = mcMarketClient.getResource(resourceId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private MCMarketAuthor loadMCMarketAuthor(int authorId) {
        ResponseEntity<MCMarketAuthor> resp = mcMarketClient.getAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    // PolyMart handling
    private Resource handlePolyMart(final int resourceId) {
        final PolyMartResource resource = loadPolyMartResource(resourceId);

        if (resource == null) {
            return null;
        }

        final PolyMartResourceData data = resource.getResponse().getResource();
        final String image = loadPolyMartImage(data.getThumbnailURL());

        final boolean isPremium = !(data.getPrice() == 0.00);

        return new Resource(
                image,
                data.getTitle(),
                data.getOwner().getId(),
                data.getOwner().getName(),
                new RatingInformation(
                        data.getReviews().getCount(),
                        (double) data.getReviews().getStars()
                ),
                data.getDownloads(),
                isPremium ? new PriceInformation(data.getPrice(), data.getCurrency().toUpperCase(Locale.ROOT)) : null,
                null
        );
    }

    private PolyMartResource loadPolyMartResource(final int resourceId) {
        final ResponseEntity<PolyMartResource> resp = polyMartClient.getResource(resourceId);
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
