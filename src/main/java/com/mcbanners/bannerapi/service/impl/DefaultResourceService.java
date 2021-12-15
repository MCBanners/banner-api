package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.CurseForgeClient;
import com.mcbanners.bannerapi.net.OreClient;
import com.mcbanners.bannerapi.net.SpigotClient;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResourceMember;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
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

@Service
@CacheConfig(cacheNames = {"resource"})
public class DefaultResourceService implements ResourceService {
    private final SpigotClient spigotClient;
    private final OreClient oreClient;
    private final CurseForgeClient curseForgeClient;

    @Autowired
    public DefaultResourceService(SpigotClient spigotClient, OreClient oreClient, CurseForgeClient curseForgeClient) {
        this.spigotClient = spigotClient;
        this.oreClient = oreClient;
        this.curseForgeClient = curseForgeClient;
    }

    @Override
    @Cacheable(unless = "#result == null")
    public Resource getResource(int resourceId, ServiceBackend backend) {
        // At this time, only Spiget supports querying by resource ID
        // Fail fast if SPIGET is not the specified ServiceBackend
        if (backend != ServiceBackend.SPIGOT && backend != ServiceBackend.CURSEFORGE) {
            return null;
        }

        if (backend == ServiceBackend.CURSEFORGE) {
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

    @Override
    @Cacheable(unless = "#result == null")
    public Resource getResource(String pluginId, ServiceBackend backend) {
        // At this time, only Ore supports querying by author name
        // Fail fast if ORE is not the specified ServiceBackend
        if (backend != ServiceBackend.ORE) {
            return null;
        }

        OreResource oreResource = loadOreResource(pluginId);
        if (oreResource == null) {
            return null;
        }

        String oreResourceIcon = loadOreResourceIcon(oreResource.getHref());
        if (oreResourceIcon == null) {
            oreResourceIcon = "";
        }

        return new Resource(
                oreResourceIcon,
                oreResource.getName(),
                -1, // not known
                oreResource.getOwner(), // username
                new RatingInformation(oreResource.getStars()),
                oreResource.getDownloads(),
                null,
                null);
    }

    private SpigotResource loadSpigotResource(int resourceId) {
        ResponseEntity<SpigotResource> resp = spigotClient.getResource(resourceId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private CurseForgeResource loadCurseForgeResource(int resourceId) {
        ResponseEntity<CurseForgeResource> resp = curseForgeClient.getResource(resourceId);
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

    private String loadCurseForgeResourceIcon(String url) {
        ResponseEntity<byte[]> resp = curseForgeClient.getResourceIcon(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }

    private OreResource loadOreResource(String pluginId) {
        ResponseEntity<OreResource> resp = oreClient.getResource(pluginId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadOreResourceIcon(String href) {
        ResponseEntity<byte[]> resp = oreClient.getResourceIcon(href);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }
}
