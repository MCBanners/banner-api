package com.mcbanners.backend.obj.generic;

public class Author {
    private final String name;
    private final int resources;
    private final String icon;
    private final int downloads;
    private final int likes;
    private final int rating;

    public Author(String name, int resources, String icon, int downloads, int likes, int rating) {
        this.name = name;
        this.resources = resources;
        this.icon = icon;
        this.downloads = downloads;
        this.likes = likes;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getResources() {
        return resources;
    }

    public String getIcon() {
        return icon;
    }

    public int getDownloads() {
        return downloads;
    }

    public int getLikes() {
        return likes;
    }

    public int getRating() {
        return rating;
    }
}
