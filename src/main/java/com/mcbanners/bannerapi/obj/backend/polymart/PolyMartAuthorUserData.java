package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolyMartAuthorUserData {
    private int id;
    private String username;
    private String type;
    private String profilePictureURL;
    private PolyMartAuthorStatistics statistics;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public PolyMartAuthorStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(PolyMartAuthorStatistics statistics) {
        this.statistics = statistics;
    }
}
