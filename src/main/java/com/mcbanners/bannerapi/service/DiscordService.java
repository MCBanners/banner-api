package com.mcbanners.bannerapi.service;

import com.mcbanners.bannerapi.net.DiscordClient;
import com.mcbanners.bannerapi.obj.backend.discord.DiscordUser;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"discord"})
public class DiscordService {
    private final DiscordClient client;

    @Autowired
    public DiscordService(DiscordClient client) {
        this.client = client;
    }

    @Cacheable(unless = "#result == null")
    public DiscordUser getUser(String id) {
        try {
            return client.getDiscordUser(id);
        } catch (Exception ex) {
            Log.warn("Failed to load Discord user " + id + " from Discord API");
            return null;
        }
    }
}
