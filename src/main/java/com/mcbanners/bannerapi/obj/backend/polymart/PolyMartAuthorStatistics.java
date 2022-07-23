package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolyMartAuthorStatistics {
    private int resourceCount;
    private int resourceDownloads;
    private int resourceRatings;
    private double resourceAverageRating;

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    public int getResourceDownloads() {
        return resourceDownloads;
    }

    public void setResourceDownloads(int resourceDownloads) {
        this.resourceDownloads = resourceDownloads;
    }

    public int getResourceRatings() {
        return resourceRatings;
    }

    public void setResourceRatings(int resourceRatings) {
        this.resourceRatings = resourceRatings;
    }

    public double getResourceAverageRating() {
        return resourceAverageRating;
    }

    public void setResourceAverageRating(double resourceAverageRating) {
        this.resourceAverageRating = resourceAverageRating;
    }
}
