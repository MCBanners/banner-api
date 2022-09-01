package com.mcbanners.bannerapi.obj.backend.spigot;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.spigot.SpigotResourceDeserializer;

@JsonDeserialize(using = SpigotResourceDeserializer.class)
public record SpigotResource(
        String id,
        String title,
        String tag,
        String currentVersion,
        String iconLink,
        String price,
        String currency,
        String downloads,
        String updates,
        String uniqueReviews,
        String totalReviews,
        String rating,
        String authorId,
        String authorUsername
) {}
