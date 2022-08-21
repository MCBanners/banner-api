package com.mcbanners.bannerapi.obj.generic;

public class Team {
    private final String name;
    private final String icon;
    private final int resourceCount;
    private final int resourceDownloads;
    private final int resourceRatings;
    private final double resourceAverageRating;

    public Team(String name, String icon, int resourceCount, int resourceDownloads, int resourceRatings, double resourceAverageRating) {
        this.name = name;
        this.icon = icon;
        this.resourceCount = resourceCount;
        this.resourceDownloads = resourceDownloads;
        this.resourceRatings = resourceRatings;
        this.resourceAverageRating = resourceAverageRating;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public int getResourceDownloads() {
        return resourceDownloads;
    }

    public int getResourceRatings() {
        return resourceRatings;
    }

    public double getResourceAverageRating() {
        return resourceAverageRating;
    }
}
