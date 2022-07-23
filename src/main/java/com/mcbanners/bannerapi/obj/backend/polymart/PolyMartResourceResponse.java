package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolyMartResourceResponse {
    private boolean success;
    private PolyMartResourceData resource;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PolyMartResourceData getResource() {
        return resource;
    }

    public void setResource(PolyMartResourceData resource) {
        this.resource = resource;
    }
}
