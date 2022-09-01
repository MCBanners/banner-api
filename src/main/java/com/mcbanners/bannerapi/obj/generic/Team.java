package com.mcbanners.bannerapi.obj.generic;

public record Team(String name, String icon, int resourceCount, int resourceDownloads, int resourceRatings,
                   double resourceAverageRating) {
}
