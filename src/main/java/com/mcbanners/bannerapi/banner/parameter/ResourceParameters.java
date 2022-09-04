package com.mcbanners.bannerapi.banner.parameter;

import com.mcbanners.bannerapi.banner.parameter.api.namespace.SpaceableParameterNamespace;
import com.mcbanners.bannerapi.banner.parameter.api.namespace.TextParameterNamespace;

import java.util.Map;

public final class ResourceParameters extends GlobalParameters {
    private final TextParameterNamespace resourceName;
    private final TextParameterNamespace authorName;
    private final TextParameterNamespace reviews;
    private final TextParameterNamespace updated;
    private final SpaceableParameterNamespace stars;
    private final TextParameterNamespace downloads;
    private final TextParameterNamespace price;

    public ResourceParameters(Map<String, String> rawParameters) {
        super(rawParameters);

        resourceName = new TextParameterNamespace("resource_name", rawParameters);
        authorName = new TextParameterNamespace("author_name", rawParameters);
        reviews = new TextParameterNamespace("reviews", rawParameters);
        updated = new TextParameterNamespace("updated", rawParameters);
        stars = new SpaceableParameterNamespace("stars", rawParameters);
        downloads = new TextParameterNamespace("downloads", rawParameters);
        price = new TextParameterNamespace("price", rawParameters);

        // Resource Name defaults
        resourceName.getX().defaultValue(104);
        resourceName.getY().defaultValue(25);
        resourceName.getFontSize().defaultValue(18);
        resourceName.getFontBold().defaultValue(true);

        // Author Name defaults
        authorName.getX().defaultValue(104);
        authorName.getY().defaultValue(42);

        // Reviews defaults
        reviews.getX().defaultValue(104);
        reviews.getY().defaultValue(62);

        // Updated defaults
        updated.getX().defaultValue(104);
        updated.getY().defaultValue(62);

        // Stars defaults
        stars.getX().defaultValue(180);
        stars.getY().defaultValue(51);

        // Downloads defaults
        downloads.getX().defaultValue(104);
        downloads.getY().defaultValue(83);

        // Price defaults
        price.getX().defaultValue(210);
        price.getY().defaultValue(83);
        price.getFontBold().defaultValue(true);
    }

    public TextParameterNamespace getResourceName() {
        return resourceName;
    }

    public TextParameterNamespace getAuthorName() {
        return authorName;
    }

    public TextParameterNamespace getReviews() {
        return reviews;
    }

    public TextParameterNamespace getUpdated() {
        return updated;
    }

    public SpaceableParameterNamespace getStars() {
        return stars;
    }

    public TextParameterNamespace getDownloads() {
        return downloads;
    }

    public TextParameterNamespace getPrice() {
        return price;
    }

    @Override
    public Map<String, Map<String, Object>> defaults() {
        Map<String, Map<String, Object>> output = super.defaults();

        output.put("resource_name", resourceName.defaults());
        output.put("author_name", authorName.defaults());
        output.put("reviews", reviews.defaults());
        output.put("updated", updated.defaults());
        output.put("stars", stars.defaults());
        output.put("downloads", downloads.defaults());
        output.put("price", price.defaults());

        return output;
    }
}
