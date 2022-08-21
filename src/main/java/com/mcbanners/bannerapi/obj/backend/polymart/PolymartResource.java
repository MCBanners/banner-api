package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolymartResource {
    private PolymartResourceResponse response;

    public PolymartResourceResponse getResponse() {
        return response;
    }

    public void setResponse(PolymartResourceResponse response) {
        this.response = response;
    }
}
