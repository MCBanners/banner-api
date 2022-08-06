package com.mcbanners.bannerapi.obj.backend.builtbybit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuiltByBitResourceBasic {
    private String result;
    private BuiltByBitResourceBasicData[] data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BuiltByBitResourceBasicData[] getData() {
        return data;
    }

    public void setData(BuiltByBitResourceBasicData[] data) {
        this.data = data;
    }
}
