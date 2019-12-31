package com.mcbanners.backend.service.impl;

import com.mcbanners.backend.net.OreClient;
import com.mcbanners.backend.net.SpigetClient;
import com.mcbanners.backend.obj.backend.ore.OreResource;
import com.mcbanners.backend.obj.backend.spiget.SpigetResource;
import com.mcbanners.backend.obj.generic.PriceInformation;
import com.mcbanners.backend.obj.generic.RatingInformation;
import com.mcbanners.backend.obj.generic.Resource;
import com.mcbanners.backend.service.ServiceBackend;
import com.mcbanners.backend.service.api.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

        return new Resource(
                oreResource.getIcon(),
                oreResource.getName(),
                oreResource.getOwner().getId(),
                null,
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
}
