package com.mcbanners.bannerapi.obj.backend.mcmarket;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MCMarketAuthorData {
    private int memberId;
    private String username;
    private int resourceCount;

    @JsonGetter("member_id")
    public int getMemberId() {
        return memberId;
    }

    @JsonSetter("member_id")
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonGetter("resource_count")
    public int getResourceCount() {
        return resourceCount;
    }

    @JsonSetter("resource_count")
    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }
}
