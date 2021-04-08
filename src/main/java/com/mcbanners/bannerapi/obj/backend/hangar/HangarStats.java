package com.mcbanners.bannerapi.obj.backend.hangar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HangarStats {
    private int views;
    private int downloads;
    private int recentViews;
    private int recentDownloads;
    private int stars;
    private int watchers;

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getRecentViews() {
        return recentViews;
    }

    public void setRecentViews(int recentViews) {
        this.recentViews = recentViews;
    }

    public int getRecentDownloads() {
        return recentDownloads;
    }

    public void setRecentDownloads(int recentDownloads) {
        this.recentDownloads = recentDownloads;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }
}
