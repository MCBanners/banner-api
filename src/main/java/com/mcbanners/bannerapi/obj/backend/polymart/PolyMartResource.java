package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolyMartResource {
    private PolyMartResourceResponse response;

    public PolyMartResourceResponse getResponse() {
        return response;
    }

    public void setResponse(PolyMartResourceResponse response) {
        this.response = response;
    }
}
