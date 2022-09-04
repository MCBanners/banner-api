package com.mcbanners.bannerapi.banner.parameter;

import com.mcbanners.bannerapi.banner.parameter.api.namespace.TextParameterNamespace;

import java.util.HashMap;
import java.util.Map;

public final class AuthorParameters extends GlobalParameters {
    private final TextParameterNamespace authorName;
    private final TextParameterNamespace resourceCount;
    private final TextParameterNamespace likes;
    private final TextParameterNamespace downloads;
    private final TextParameterNamespace reviews;

    public AuthorParameters(Map<String, String> rawParameters) {
        super(rawParameters);

        authorName = new TextParameterNamespace("author_name", rawParameters);
        resourceCount = new TextParameterNamespace("resource_count", rawParameters);
        likes = new TextParameterNamespace("likes", rawParameters);
        downloads = new TextParameterNamespace("downloads", rawParameters);
        reviews = new TextParameterNamespace("reviews", rawParameters);

        // Author Name defaults
        authorName.getX().defaultValue(104);
        authorName.getY().defaultValue(22);
        authorName.getFontSize().defaultValue(18);
        authorName.getFontBold().defaultValue(true);

        // Resource Count Defaults
        resourceCount.getX().defaultValue(104);
        resourceCount.getY().defaultValue(38);

        // Likes Defaults
        likes.getX().defaultValue(104);
        likes.getY().defaultValue(55);

        // Downloads defaults
        downloads.getX().defaultValue(104);
        downloads.getY().defaultValue(72);

        // Reviews defaults
        reviews.getX().defaultValue(104);
        reviews.getY().defaultValue(89);
    }

    public TextParameterNamespace getAuthorName() {
        return authorName;
    }

    public TextParameterNamespace getResourceCount() {
        return resourceCount;
    }

    public TextParameterNamespace getLikes() {
        return likes;
    }

    public TextParameterNamespace getDownloads() {
        return downloads;
    }

    public TextParameterNamespace getReviews() {
        return reviews;
    }

    @Override
    public Map<String, Map<String, Object>> defaults() {
        Map<String, Map<String, Object>> output = super.defaults();

        output.put("author_name", authorName.defaults());
        output.put("resource_count", resourceCount.defaults());
        output.put("likes", likes.defaults());
        output.put("downloads", downloads.defaults());
        output.put("reviews", reviews.defaults());

        return output;
    }
}
