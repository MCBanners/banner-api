package com.mcbanners.bannerapi.obj.backend.discord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.discord.DiscordUserDeserializer;

@JsonDeserialize(using = DiscordUserDeserializer.class)
public record DiscordUser(
        long id,
        String name,
        String activity,
        String status,
        long created,
        String icon
) {
}
