package com.mcbanners.backend.obj.spiget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpigetResource {
    private String description;
    private int likes;
    private String name;
    private String tag;
    private SpigetAuthor author;
    private SpigetResourceRating rating;
    private int downloads;
    private SpigetIcon icon;
    private boolean premium;
    private double price;
    private String currency;
    private List<SpigetResourceReview> reviews;
    private int id;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public SpigetAuthor getAuthor() {
        return author;
    }

    public void setAuthor(SpigetAuthor author) {
        this.author = author;
    }

    public SpigetResourceRating getRating() {
        return rating;
    }

    public void setRating(SpigetResourceRating rating) {
        this.rating = rating;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public SpigetIcon getIcon() {
        return icon;
    }

    public void setIcon(SpigetIcon icon) {
        this.icon = icon;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
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

    public List<SpigetResourceReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<SpigetResourceReview> reviews) {
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
