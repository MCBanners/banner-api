package com.mcbanners.backend.obj.generic;

public class Resource {
    private final String logo;
    private final String name;
    private final int authorId;
    private final RatingInformation rating;
    private final int downloadCount;
    private final PriceInformation price;

    public Resource(String logo, String name, int authorId, RatingInformation rating, int downloadCount, PriceInformation price) {
        this.logo = logo;
        this.name = name;
        this.authorId = authorId;
        this.rating = rating;
        this.downloadCount = downloadCount;
        this.price = price;
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

    public RatingInformation getRating() {
        return rating;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public PriceInformation getPrice() {
        return price;
    }
}
