package com.mcbanners.bannerapi.obj.backend.mcmarket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MCMarketResource {
    private String result;
    private MCMarketResourceData data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public MCMarketResourceData getData() {
        return data;
    }

    public void setData(MCMarketResourceData data) {
        this.data = data;
    }
}
