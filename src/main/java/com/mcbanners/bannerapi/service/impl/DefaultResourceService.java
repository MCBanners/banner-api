package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.OreClient;
import com.mcbanners.bannerapi.net.SpigetClient;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.obj.backend.spiget.SpigetResource;
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
    private final SpigetClient spigetClient;
    private final OreClient oreClient;

    @Autowired
    public DefaultResourceService(SpigetClient spigetClient, OreClient oreClient) {
        this.spigetClient = spigetClient;
        this.oreClient = oreClient;
    }

    @Override
    @Cacheable
    public Resource getResource(int resourceId, ServiceBackend backend) {
        // At this time, only Spiget supports querying by resource ID
        // Fail fast if SPIGET is not the specified ServiceBackend
        if (backend != ServiceBackend.SPIGET) {
            return null;
        }

        SpigetResource spigetResource = loadSpigetResource(resourceId);

        if (spigetResource == null) {
            return null;
        }

        return new Resource(
                spigetResource.getIcon().getData(),
                spigetResource.getName(),
                spigetResource.getAuthor().getId(),
                spigetResource.getAuthor().getName(),
                new RatingInformation(
                        spigetResource.getRating().getCount(),
                        spigetResource.getRating().getAverage()
                ),
                spigetResource.getDownloads(),
                spigetResource.isPremium() ? new PriceInformation(
                        spigetResource.getPrice(),
                        spigetResource.getCurrency()
                ) : null
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

    private SpigetResource loadSpigetResource(int resourceId) {
        ResponseEntity<SpigetResource> resp = spigetClient.getResource(resourceId);
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
