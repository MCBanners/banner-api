package com.mcbanners.bannerapi.obj.backend.ore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.ore.OreResourceDeserializer;

@JsonDeserialize(using = OreResourceDeserializer.class)
public record OreResource(
        String pluginId,
        String name,
        String owner,
        String slug,
        int views,
        int downloads,
        int stars,
        String lastUpdated,
        String iconUrl
) {
}
