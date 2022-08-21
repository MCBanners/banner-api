package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolymartResourceData {
    private int id;
    private String title;
    private PolymartResourceOwner owner;
    private String team;
    private double price;
    private String currency;
    private int downloads;
    private String thumbnailURL;
    private PolymartResourceReviews reviews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PolymartResourceOwner getOwner() {
        return owner;
    }

    public void setOwner(PolymartResourceOwner owner) {
        this.owner = owner;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public PolymartResourceReviews getReviews() {
        return reviews;
    }

    public void setReviews(PolymartResourceReviews reviews) {
        this.reviews = reviews;
    }
}
