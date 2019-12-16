package com.mcbanners.backend.spiget.svc;

import com.mcbanners.backend.obj.SpigetResource;
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
    public SpigetResource getResource(int resourceId) {
        try {
            return loadResource(resourceId);
        } catch (NullPointerException ex) {
            return null;
        }
    }

    private SpigetResource loadResource(int resourceId) {
        return client.getResource(resourceId).getBody();
    }
}
