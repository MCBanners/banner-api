package com.mcbanners.backend.spiget.svc;

import com.mcbanners.backend.obj.PriceInformation;
import com.mcbanners.backend.obj.RatingInformation;
import com.mcbanners.backend.obj.Resource;
import com.mcbanners.backend.obj.spiget.SpigetResource;
import com.mcbanners.backend.spiget.SpigetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"resource"})
public class DefaultSpigetResourceService implements SpigetResourceService {
    private final SpigetClient client;

    @Autowired
    public DefaultSpigetResourceService(SpigetClient client) {
        this.client = client;
    }

    @Cacheable
    @Override
    public Resource getResource(int resourceId) {
        try {
            SpigetResource resource = loadResource(resourceId);
            return new Resource(
                    resource.getIcon().getData(),
                    resource.getName(),
                    resource.getAuthor().getId(),
                    new RatingInformation(resource.getRating().getCount(), resource.getRating().getAverage()),
                    resource.getDownloads(),
                    resource.isPremium() ? new PriceInformation(resource.getPrice(), resource.getCurrency()) : null
            );
        } catch (NullPointerException ex) {
            return null;
        }
    }

    private SpigetResource loadResource(int resourceId) {
        return client.getResource(resourceId).getBody();
    }
}
