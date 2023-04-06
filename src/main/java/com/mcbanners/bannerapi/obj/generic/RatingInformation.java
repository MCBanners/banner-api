package com.mcbanners.bannerapi.obj.generic;

public record RatingInformation(int count, Double averageRating) {
    public RatingInformation(int count) {
        this(count, null);
    }
}
