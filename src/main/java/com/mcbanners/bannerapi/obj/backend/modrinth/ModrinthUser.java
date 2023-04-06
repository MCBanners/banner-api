package com.mcbanners.bannerapi.obj.backend.modrinth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.modrinth.ModrinthUserDeserializer;

@JsonDeserialize(using = ModrinthUserDeserializer.class)
public record ModrinthUser(
        String id,
        String githubId,
        String username,
        String name,
        String avatarUrl
) {
}
