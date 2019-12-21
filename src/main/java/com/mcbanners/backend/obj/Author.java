package com.mcbanners.backend.obj;

public class Author {
    private final String name;
    private final int resources;

    public Author(String name, int resources) {
        this.name = name;
        this.resources = resources;
    }

    public String getName() {
        return name;
    }

    public int getResources() {
        return resources;
    }
}
