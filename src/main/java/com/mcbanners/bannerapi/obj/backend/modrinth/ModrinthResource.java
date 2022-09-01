package com.mcbanners.bannerapi.obj.backend.modrinth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.modrinth.ModrinthResourceDeserializer;

@JsonDeserialize(using = ModrinthResourceDeserializer.class)
public record ModrinthResource(
        String slug,
        String team,
        String title,
        String updated,
        int downloads,
        int followers,
        String iconUrl
) {}
