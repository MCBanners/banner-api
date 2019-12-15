package com.mcbanners.backend.svc;

import com.mcbanners.backend.spiget.SpigetClient;
import com.mcbanners.backend.spiget.obj.SpigetResource;
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
        return loadResource(resourceId);
    }

    private SpigetResource loadResource(int resourceId) {
        System.out.println("Loading resource from Spiget...");
        return client.getResource(resourceId).getBody();
    }
}
