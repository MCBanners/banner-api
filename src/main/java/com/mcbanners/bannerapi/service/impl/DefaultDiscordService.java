package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.DiscordClient;
import com.mcbanners.bannerapi.obj.backend.discord.DiscordUser;
import com.mcbanners.bannerapi.service.api.DiscordService;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"discord"})
public class DefaultDiscordService implements DiscordService {
    private final DiscordClient client;

    @Autowired
    public DefaultDiscordService(DiscordClient client) {
        this.client = client;
    }

    @Override
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
