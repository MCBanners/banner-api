package com.mcbanners.backend.obj;

public class Author {
    private final String name;
    private final int resources;
    private final String icon;
    private final int downloads;

    public Author(String name, int resources, String icon, int downloads) {
        this.name = name;
        this.resources = resources;
        this.icon = icon;
        this.downloads = downloads;
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
}
