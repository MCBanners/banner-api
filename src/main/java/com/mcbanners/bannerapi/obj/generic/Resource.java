package com.mcbanners.bannerapi.obj.generic;

public class Resource {
    private final String logo;
    private final String name;
    private final int authorId;
    private final String authorName;
    private final RatingInformation rating;
    private final int downloadCount;
    private final PriceInformation price;
    private final String lastUpdated;

    public Resource(String logo, String name, int authorId, String authorName, RatingInformation rating, int downloadCount, PriceInformation price, String lastUpdated) {
        this.logo = logo;
        this.name = name;
        this.authorId = authorId;
        this.authorName = authorName;
        this.rating = rating;
        this.downloadCount = downloadCount;
        this.price = price;
        this.lastUpdated = lastUpdated;
    }

    public String getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public RatingInformation getRating() {
        return rating;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public PriceInformation getPrice() {
        return price;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
}
