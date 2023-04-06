package com.mcbanners.bannerapi.banner.parameter;

import com.mcbanners.bannerapi.banner.parameter.api.namespace.TextParameterNamespace;

import java.util.Map;

public final class MemberParameters extends GlobalParameters {
    private final TextParameterNamespace memberName;
    private final TextParameterNamespace rank;
    private final TextParameterNamespace joined;
    private final TextParameterNamespace posts;
    private final TextParameterNamespace likes;

    public MemberParameters(Map<String, String> rawParameters) {
        super(rawParameters);

        memberName = new TextParameterNamespace("member_name", rawParameters);
        rank = new TextParameterNamespace("rank", rawParameters);
        joined = new TextParameterNamespace("joined", rawParameters);
        posts = new TextParameterNamespace("posts", rawParameters);
        likes = new TextParameterNamespace("likes", rawParameters);

        // Member Name defaults
        memberName.getX().defaultValue(104);
        memberName.getY().defaultValue(22);
        memberName.getFontSize().defaultValue(18);
        memberName.getFontBold().defaultValue(true);

        // Rank defaults
        rank.getX().defaultValue(104);
        rank.getY().defaultValue(37);

        // Joined defaults
        joined.getX().defaultValue(104);
        joined.getY().defaultValue(55);

        // Posts defaults
        posts.getX().defaultValue(104);
        posts.getY().defaultValue(72);

        // Likes defaults
        likes.getX().defaultValue(104);
        likes.getY().defaultValue(89);
    }

    public TextParameterNamespace getMemberName() {
        return memberName;
    }

    public TextParameterNamespace getRank() {
        return rank;
    }

    public TextParameterNamespace getJoined() {
        return joined;
    }

    public TextParameterNamespace getPosts() {
        return posts;
    }

    public TextParameterNamespace getLikes() {
        return likes;
    }

    @Override
    public Map<String, Map<String, Object>> defaults() {
        Map<String, Map<String, Object>> output = super.defaults();

        output.put("member_name", memberName.defaults());
        output.put("rank", rank.defaults());
        output.put("joined", joined.defaults());
        output.put("posts", posts.defaults());
        output.put("likes", likes.defaults());

        return output;
    }
}
