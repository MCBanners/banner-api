package com.mcbanners.bannerapi.obj.backend.spigot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpigotResource {
    private String id;
    private String title;
    private String tag;
    private SpigotAuthor author;
    private SpigotPremium premium;
    private SpigotResourceStats stats;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public SpigotAuthor getAuthor() {
        return author;
    }

    public void setAuthor(SpigotAuthor author) {
        this.author = author;
    }

    public SpigotPremium getPremium() {
        return premium;
    }

    public void setPremium(SpigotPremium premium) {
        this.premium = premium;
    }

    public SpigotResourceStats getStats() {
        return stats;
    }

    public void setStats(SpigotResourceStats stats) {
        this.stats = stats;
    }
}
