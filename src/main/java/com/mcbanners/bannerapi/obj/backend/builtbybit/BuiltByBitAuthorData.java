package com.mcbanners.bannerapi.obj.backend.builtbybit;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuiltByBitAuthorData {
    private int memberId;
    private String username;
    private int resourceCount;
    private String avatar_url;

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

    @JsonGetter("avatar_url")
    public String getAvatarUrl() {
        return avatar_url;
    }

    @JsonSetter("avatar_url")
    public void setAvatarUrl(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
