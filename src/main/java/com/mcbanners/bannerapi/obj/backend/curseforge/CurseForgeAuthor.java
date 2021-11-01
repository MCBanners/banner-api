package com.mcbanners.bannerapi.obj.backend.curseforge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurseForgeAuthor {
    private List<CurseForgeProject> projects;
    private String username;
    private int id;

    public List<CurseForgeProject> getProjects() {
        return projects;
    }

    public void setProjects(List<CurseForgeProject> projects) {
        this.projects = projects;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
