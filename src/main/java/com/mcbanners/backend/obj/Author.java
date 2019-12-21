package com.mcbanners.backend.obj;

public class Author {
    private final String name;
    private final int resources;
    private final String icon;

    public Author(String name, int resources, String icon) {
        this.name = name;
        this.resources = resources;
        this.icon = icon;
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
}
