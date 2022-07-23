package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolyMartAuthor {
    private PolyMartAuthorResponse response;

    public PolyMartAuthorResponse getResponse() {
        return response;
    }

    public void setResponse(PolyMartAuthorResponse response) {
        this.response = response;
    }
}
