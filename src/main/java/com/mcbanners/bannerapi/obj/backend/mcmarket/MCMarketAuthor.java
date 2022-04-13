package com.mcbanners.bannerapi.obj.backend.mcmarket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MCMarketAuthor {
    private String result;
    private MCMarketAuthorData data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public MCMarketAuthorData getData() {
        return data;
    }

    public void setData(MCMarketAuthorData data) {
        this.data = data;
    }
}
