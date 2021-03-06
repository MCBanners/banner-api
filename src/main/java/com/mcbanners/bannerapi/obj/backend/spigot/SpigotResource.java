package com.mcbanners.bannerapi.obj.backend.spigot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpigotResource {
    private String id;
    private String title;
    private String tag;
    private String current_version;
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

    public String getCurrent_version() {
        return current_version;
    }

    public void setCurrent_version(String current_version) {
        this.current_version = current_version;
    }
}
