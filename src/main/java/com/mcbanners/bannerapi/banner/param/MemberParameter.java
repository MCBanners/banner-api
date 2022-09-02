package com.mcbanners.bannerapi.banner.param;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.util.ParamUtil;

import java.util.Map;

public enum MemberParameter implements BannerParameter<Object> {
    MEMBER_NAME_X("member_name__x", 104, int.class),
    MEMBER_NAME_Y("member_name__y", 22, int.class),
    MEMBER_NAME_FONT_SIZE("member_name__font_size", 18, int.class),
    MEMBER_NAME_BOLD("member_name__bold", true, boolean.class),
    MEMBER_NAME_TEXT_ALIGN("member_name__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    MEMBER_NAME_FONT_FACE("member_name__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    RANK_X("rank__x", 104, int.class),
    RANK_Y("rank__y", 37, int.class),
    RANK_FONT_SIZE("rank__font_size", 14, int.class),
    RANK_BOLD("rank__bold", false, boolean.class),
    RANK_TEXT_ALIGN("rank__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    RANK_FONT_FACE("rank__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    JOINED_X("joined__x", 104, int.class),
    JOINED_Y("joined__y", 55, int.class),
    JOINED_FONT_SIZE("joined__font_size", 14, int.class),
    JOINED_BOLD("joined__bold", false, boolean.class),
    JOINED_TEXT_ALIGN("joined__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    JOINED_FONT_FACE("joined__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    POSTS_X("posts__x", 104, int.class),
    POSTS_Y("posts__y", 72, int.class),
    POSTS_FONT_SIZE("posts__font_size", 14, int.class),
    POSTS_BOLD("posts__bold", false, boolean.class),
    POSTS_TEXT_ALIGN("posts__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    POSTS_FONT_FACE("posts__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    LIKES_X("likes__x", 104, int.class),
    LIKES_Y("likes__y", 89, int.class),
    LIKES_FONT_SIZE("likes__font_size", 14, int.class),
    LIKES_BOLD("likes__bold", false, boolean.class),
    LIKES_TEXT_ALIGN("likes__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    LIKES_FONT_FACE("likes__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class);

    private final String key;
    private final Object def;
    private final Class<?> type;

    MemberParameter(String key, Object def, Class<?> type) {
        this.key = key;
        this.def = def;
        this.type = type;
    }

    public static Map<MemberParameter, Object> parse(Map<String, String> rawParams) {
        return ParamUtil.parse(MemberParameter.class, rawParams);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Object getDefault() {
        return def;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
