package com.mcbanners.bannerapi.obj.generic;

public class RatingInformation {
    private final int count;
    private final Double averageRating;

    public RatingInformation(int count) {
        this(count, null);
    }

    public RatingInformation(int count, Double average) {
        this.count = count;
        this.averageRating = average;
    }

    public int getCount() {
        return count;
    }

    public Double getAverageRating() {
        return averageRating;
    }
}
