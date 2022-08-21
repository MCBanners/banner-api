package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolymartAuthorResponse {
    private boolean success;
    private PolymartAuthorUserData user;

    public PolymartAuthorUserData getUser() {
        return user;
    }

    public void setUser(PolymartAuthorUserData user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
