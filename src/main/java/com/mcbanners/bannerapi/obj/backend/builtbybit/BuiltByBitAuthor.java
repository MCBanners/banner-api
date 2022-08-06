package com.mcbanners.bannerapi.obj.backend.builtbybit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuiltByBitAuthor {
    private String result;
    private BuiltByBitAuthorData data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BuiltByBitAuthorData getData() {
        return data;
    }

    public void setData(BuiltByBitAuthorData data) {
        this.data = data;
    }
}
