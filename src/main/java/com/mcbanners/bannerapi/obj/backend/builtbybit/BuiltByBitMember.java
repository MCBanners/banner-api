package com.mcbanners.bannerapi.obj.backend.builtbybit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuiltByBitMember {
    private String result;
    private BuiltByBitMemberData data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BuiltByBitMemberData getData() {
        return data;
    }

    public void setData(BuiltByBitMemberData data) {
        this.data = data;
    }
}
