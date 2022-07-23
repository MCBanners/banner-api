package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolyMartAuthorResponse {
    private boolean success;
    private PolyMartAuthorUserData user;

    public PolyMartAuthorUserData getUser() {
        return user;
    }

    public void setUser(PolyMartAuthorUserData user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
