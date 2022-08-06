package com.mcbanners.bannerapi.obj.generic;

public class Member {
    private final String name;
    private final String rank;
    private final String joinDate;
    private final String icon;
    private final int posts;
    private final int positiveFeedback;
    private final int negativeFeedback;

    public Member(String name, String rank, String joinDate, String icon, int posts, int positiveFeedback, int negativeFeedback) {
        this.name = name;
        this.rank = rank;
        this.joinDate = joinDate;
        this.icon = icon;
        this.posts = posts;
        this.positiveFeedback = positiveFeedback;
        this.negativeFeedback = negativeFeedback;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getIcon() {
        return icon;
    }

    public int getPosts() {
        return posts;
    }

    public int getPositiveFeedback() {
        return positiveFeedback;
    }

    public int getNegativeFeedback() {
        return negativeFeedback;
    }
}
