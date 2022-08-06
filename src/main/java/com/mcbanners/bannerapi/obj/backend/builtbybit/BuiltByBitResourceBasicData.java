package com.mcbanners.bannerapi.obj.backend.builtbybit;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuiltByBitResourceBasicData {
    private int resourceId;
    private int authorId;
    private String title;
    private String tagLine;
    private double price;
    private String currency;
    private int purchaseCount;
    private int downloadCount;
    private int reviewCount;
    private double reviewAverage;

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

    @JsonGetter("purchase_count")
    public int getPurchaseCount() {
        return purchaseCount;
    }

    @JsonSetter("purchase_count")
    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    @JsonGetter("download_count")
    public int getDownloadCount() {
        return downloadCount;
    }

    @JsonSetter("download_count")
    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    @JsonGetter("review_count")
    public int getReviewCount() {
        return reviewCount;
    }

    @JsonSetter("review_count")
    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    @JsonGetter("review_average")
    public double getReviewAverage() {
        return reviewAverage;
    }

    @JsonSetter("review_average")
    public void setReviewAverage(double reviewAverage) {
        this.reviewAverage = reviewAverage;
    }
}
