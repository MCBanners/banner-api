package com.mcbanners.backend.service.impl;

import com.mcbanners.backend.net.SpigetClient;
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

    @Autowired
    public DefaultResourceService(SpigetClient client) {
        this.spigetClient = client;
    }

    @Override
    @Cacheable
    public Resource getResource(int resourceId, ServiceBackend backend) {
        Resource resource;

        switch (backend) {
            case SPIGET:
                SpigetResource spigetResource = loadSpigetResource(resourceId);
                if (spigetResource == null) {
                    return null;
                }

                resource = new Resource(
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

                break;
            case ORE:
            default:
                throw new RuntimeException("not yet implemented");
        }

        return resource;
    }

    private SpigetResource loadSpigetResource(int resourceId) {
        ResponseEntity<SpigetResource> resp = spigetClient.getResource(resourceId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }
}
