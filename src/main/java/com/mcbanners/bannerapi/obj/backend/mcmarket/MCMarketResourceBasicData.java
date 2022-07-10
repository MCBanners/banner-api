package com.mcbanners.bannerapi.obj.backend.mcmarket;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MCMarketResourceBasicData {
    private int resourceId;
    private int authorId;
    private String title;
    private String tagLine;
    private double price;
    private String currency;

    @JsonGetter("resource_id")
    public int getResourceId() {
        return resourceId;
    }

    @JsonSetter("resource_id")
    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @JsonGetter("author_id")
    public int getAuthorId() {
        return authorId;
    }

    @JsonSetter("author_id")
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonGetter("tag_line")
    public String getTagLine() {
        return tagLine;
    }

    @JsonSetter("tag_line")
    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
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
}
