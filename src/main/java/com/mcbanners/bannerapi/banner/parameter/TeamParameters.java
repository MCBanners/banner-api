package com.mcbanners.bannerapi.banner.parameter;

import com.mcbanners.bannerapi.banner.parameter.api.namespace.TextParameterNamespace;

import java.util.Map;

public final class TeamParameters extends GlobalParameters {
    private final TextParameterNamespace teamName;
    private final TextParameterNamespace resourceCount;
    private final TextParameterNamespace downloads;
    private final TextParameterNamespace ratings;

    public TeamParameters(Map<String, String> rawParameters) {
        super(rawParameters);

        teamName = new TextParameterNamespace("team_name", rawParameters);
        resourceCount = new TextParameterNamespace("resource_count", rawParameters);
        downloads = new TextParameterNamespace("downloads", rawParameters);
        ratings = new TextParameterNamespace("ratings", rawParameters);

        // Team Name defaults
        teamName.getX().defaultValue(104);
        teamName.getY().defaultValue(22);
        teamName.getFontSize().defaultValue(18);
        teamName.getFontBold().defaultValue(true);

        // Resource Count Defaults
        resourceCount.getX().defaultValue(104);
        resourceCount.getY().defaultValue(38);

        // Downloads defaults
        downloads.getX().defaultValue(104);
        downloads.getY().defaultValue(72);

        // Ratings defaults
        ratings.getX().defaultValue(104);
        ratings.getY().defaultValue(89);
    }

    public TextParameterNamespace getTeamName() {
        return teamName;
    }

    public TextParameterNamespace getResourceCount() {
        return resourceCount;
    }

    public TextParameterNamespace getDownloads() {
        return downloads;
    }

    public TextParameterNamespace getRatings() {
        return ratings;
    }

    @Override
    public Map<String, Map<String, Object>> defaults() {
        Map<String, Map<String, Object>> output = super.defaults();

        output.put("team_name", teamName.defaults());
        output.put("resource_count", resourceCount.defaults());
        output.put("downloads", downloads.defaults());
        output.put("ratings", ratings.defaults());

        return output;
    }
}
