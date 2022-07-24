package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolyMartResourceData {
    private int id;
    private String title;
    private PolyMartResourceOwner owner;
    private String team;
    private double price;
    private String currency;
    private int downloads;
    private String thumbnailURL;
    private PolyMartResourceReviews reviews;

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

    public PolyMartResourceOwner getOwner() {
        return owner;
    }

    public void setOwner(PolyMartResourceOwner owner) {
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

    public PolyMartResourceReviews getReviews() {
        return reviews;
    }

    public void setReviews(PolyMartResourceReviews reviews) {
        this.reviews = reviews;
    }
}
