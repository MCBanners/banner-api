package com.mcbanners.bannerapi.obj.backend.spiget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpigetResourceReview {
    private SpigetAuthor author;
    private SpigetResourceRating rating;
    private String message;
    private int id;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
