package com.mcbanners.bannerapi.obj.backend.mcmarket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MCMarketResourceBasic {
    private String result;
    private MCMarketResourceBasicData[] data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public MCMarketResourceBasicData[] getData() {
        return data;
    }

    public void setData(MCMarketResourceBasicData[] data) {
        this.data = data;
    }
}
