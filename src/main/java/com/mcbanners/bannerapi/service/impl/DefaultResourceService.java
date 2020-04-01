package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.OreClient;
import com.mcbanners.bannerapi.net.SpigotClient;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotResource;
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

    @Autowired
    public DefaultResourceService(SpigotClient spigotClient, OreClient oreClient) {
        this.spigotClient = spigotClient;
        this.oreClient = oreClient;
    }

    @Override
    @Cacheable
    public Resource getResource(int resourceId, ServiceBackend backend) {
        // At this time, only Spiget supports querying by resource ID
        // Fail fast if SPIGET is not the specified ServiceBackend
        if (backend != ServiceBackend.SPIGOT) {
            return null;
        }

        SpigotResource spigotResource = loadSpigotResource(resourceId);

        if (spigotResource == null) {
            return null;
        }

        return new Resource(
                "",
                spigotResource.getTitle(),
                Integer.parseInt(spigotResource.getAuthor().getId()),
                spigotResource.getAuthor().getUsername(),
                new RatingInformation(
                        0,
                        Double.parseDouble(spigotResource.getStats().getRating())
                ),
                Integer.parseInt(spigotResource.getStats().getDownloads()),
                null
        );
    }

    @Override
    @Cacheable
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
                null
        );
    }

    private SpigotResource loadSpigotResource(int resourceId) {
        ResponseEntity<SpigotResource> resp = spigotClient.getResource(resourceId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
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
