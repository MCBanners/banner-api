package com.mcbanners.bannerapi.obj.backend.ore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OreAuthor {
    private int id;
    private String name;
    private String avatarUrl;
    private OreResource[] projects;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public OreResource[] getProjects() {
        return projects;
    }

    public void setProjects(OreResource[] projects) {
        this.projects = projects;
    }
}
