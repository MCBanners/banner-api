package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.polymart.PolymartResourceDeserializer;

@JsonDeserialize(using = PolymartResourceDeserializer.class)
public record PolymartResource(
        int id,
        String title,
        String ownerName,
        int ownerId,
        String ownerType,
        String ownerUrl,
        double price,
        String currency,
        int downloads,
        String thumbnailURL,
        int reviewCount,
        int stars
) {}
