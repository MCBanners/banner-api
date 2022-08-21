package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolymartResourceResponse {
    private boolean success;
    private PolymartResourceData resource;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PolymartResourceData getResource() {
        return resource;
    }

    public void setResource(PolymartResourceData resource) {
        this.resource = resource;
    }
}
