package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolymartAuthor {
    private PolymartAuthorResponse response;

    public PolymartAuthorResponse getResponse() {
        return response;
    }

    public void setResponse(PolymartAuthorResponse response) {
        this.response = response;
    }
}
