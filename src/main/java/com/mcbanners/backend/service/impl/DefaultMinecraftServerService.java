package com.mcbanners.backend.service.impl;

import com.mcbanners.backend.net.McAPIClient;
import com.mcbanners.backend.obj.backend.mcapi.MinecraftServer;
import com.mcbanners.backend.service.api.MinecraftServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"server"})
public class DefaultMinecraftServerService implements MinecraftServerService {
    private final McAPIClient client;

    @Autowired
    public DefaultMinecraftServerService(McAPIClient client) {
        this.client = client;
    }

    @Override
    public MinecraftServer getServer(String host, int port) {
        return loadServer(host, port);
    }

    private MinecraftServer loadServer(String host, int port) {
        ResponseEntity<MinecraftServer> resp = client.getServer(host, port);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }
}
