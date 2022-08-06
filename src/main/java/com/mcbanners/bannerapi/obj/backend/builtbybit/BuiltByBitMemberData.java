package com.mcbanners.bannerapi.obj.backend.builtbybit;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuiltByBitMemberData {
    private int memberId;
    private String username;
    private long joinDate;
    private boolean premium;
    private boolean supreme;
    private boolean ultimate;
    private String avatarUrl;
    private int postCount;
    private int feedbackPositive;
    private int feedbackNegative;

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

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isSupreme() {
        return supreme;
    }

    public void setSupreme(boolean supreme) {
        this.supreme = supreme;
    }

    public boolean isUltimate() {
        return ultimate;
    }

    public void setUltimate(boolean ultimate) {
        this.ultimate = ultimate;
    }

    @JsonGetter("join_date")
    public long getJoinDate() {
        return joinDate;
    }

    @JsonSetter("join_date")
    public void setJoinDate(long joinDate) {
        this.joinDate = joinDate;
    }

    @JsonGetter("avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @JsonSetter("avatar_url")
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @JsonGetter("post_count")
    public int getPostCount() {
        return postCount;
    }

    @JsonSetter("post_count")
    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    @JsonGetter("feedback_positive")
    public int getFeedbackPositive() {
        return feedbackPositive;
    }

    @JsonSetter("feedback_positive")
    public void setFeedbackPositive(int feedbackPositive) {
        this.feedbackPositive = feedbackPositive;
    }

    @JsonGetter("feedback_negative")
    public int getFeedbackNegative() {
        return feedbackNegative;
    }

    @JsonSetter("feedback_negative")
    public void setFeedbackNegative(int feedbackNegative) {
        this.feedbackNegative = feedbackNegative;
    }
}
