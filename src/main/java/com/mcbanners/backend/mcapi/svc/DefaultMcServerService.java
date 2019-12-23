package com.mcbanners.backend.mcapi.svc;

import com.mcbanners.backend.mcapi.McAPIClient;
import com.mcbanners.backend.obj.McServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"server"})
public class DefaultMcServerService implements McServerService {
    private final McAPIClient client;

    @Autowired
    public DefaultMcServerService(McAPIClient client) {
        this.client = client;
    }


    @Override
    public McServer getServer(String host) {
        try {
            return loadServer(host);
        } catch (NullPointerException ex) {
            return null;
        }
    }

    private McServer loadServer(String hostAddress) {
        return client.getServer(hostAddress).getBody();
    }
}
