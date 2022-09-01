package com.mcbanners.bannerapi.obj.backend.spigot;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.spigot.SpigotAuthorDeserializer;

@JsonDeserialize(using = SpigotAuthorDeserializer.class)
public record SpigotAuthor(
        String id,
        String username,
        String resourceCount,
        String avatar
) {}
