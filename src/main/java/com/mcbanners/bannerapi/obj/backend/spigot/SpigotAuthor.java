package com.mcbanners.bannerapi.obj.backend.spigot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpigotAuthor {
    private String id;
    private String username;
    private String resource_count;
    private SpigotAvatar avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResource_count() {
        return resource_count;
    }

    public void setResource_count(String resource_count) {
        this.resource_count = resource_count;
    }

    public SpigotAvatar getAvatar() {
        return avatar;
    }

    public void setAvatar(SpigotAvatar avatar) {
        this.avatar = avatar;
    }
}
