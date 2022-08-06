package com.mcbanners.bannerapi.obj.backend.builtbybit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuiltByBitResource {
    private String result;
    private BuiltByBitResourceData data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BuiltByBitResourceData getData() {
        return data;
    }

    public void setData(BuiltByBitResourceData data) {
        this.data = data;
    }
}
