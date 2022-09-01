package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.polymart.PolymartAuthorDeserializer;

@JsonDeserialize(using = PolymartAuthorDeserializer.class)
public record PolymartAuthor(
        int id,
        String username,
        String type,
        String profilePictureURL,
        int resourceCount,
        int resourceDownloads,
        int resourceRatings,
        double resourceAverageRating
) {}
