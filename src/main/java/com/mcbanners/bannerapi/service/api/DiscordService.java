package com.mcbanners.bannerapi.service.api;

import com.mcbanners.bannerapi.obj.backend.discord.DiscordUser;

public interface DiscordService {

    /**
     * Get a Discord user by their ID
     *
     * @param id the ID of the user
     * @return the DiscordUser object that represents a user
     */
    DiscordUser getUser(final String id);
}
