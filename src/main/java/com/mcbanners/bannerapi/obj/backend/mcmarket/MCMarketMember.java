package com.mcbanners.bannerapi.obj.backend.mcmarket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MCMarketMember {
    private String result;
    private MCMarketMemberData data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public MCMarketMemberData getData() {
        return data;
    }

    public void setData(MCMarketMemberData data) {
        this.data = data;
    }
}
