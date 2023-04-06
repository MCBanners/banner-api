package com.mcbanners.bannerapi.obj.backend.mcapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.mcapi.MinecraftServerDeserializer;

@JsonDeserialize(using = MinecraftServerDeserializer.class)
public record MinecraftServer(
        String host,
        int port,
        String version,
        int onlinePlayers,
        int maxPlayers,
        String rawMotd,
        String formattedMotd,
        String icon
) {
}
