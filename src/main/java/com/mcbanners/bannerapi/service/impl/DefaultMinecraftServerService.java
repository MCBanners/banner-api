package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.McAPIClient;
import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;
import com.mcbanners.bannerapi.service.api.MinecraftServerService;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
        try {
            return client.getMinecraftServer(host, port);
        } catch (Exception e) {
            Log.warn("Failed to load server " + host + ":" + port + " from McAPI");
            return null;
        }
    }
}
