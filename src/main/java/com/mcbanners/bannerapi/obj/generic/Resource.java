package com.mcbanners.bannerapi.obj.generic;

public record Resource(String logo, String name, int authorId, String authorName, RatingInformation rating,
                       int downloadCount, PriceInformation price, String lastUpdated) {
}
