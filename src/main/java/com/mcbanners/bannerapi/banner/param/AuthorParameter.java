package com.mcbanners.bannerapi.banner.param;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.util.ParamUtil;

import java.util.Map;

public enum AuthorParameter implements BannerParameter<Object> {
    AUTHOR_NAME_X("author_name__x", 104, int.class),
    AUTHOR_NAME_Y("author_name__y", 22, int.class),
    AUTHOR_NAME_FONT_SIZE("author_name__font_size", 18, int.class),
    AUTHOR_NAME_BOLD("author_name__bold", true, boolean.class),
    AUTHOR_NAME_TEXT_ALIGN("author_name__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    AUTHOR_NAME_FONT_FACE("author_name__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    RESOURCE_NAME_X("resource_name__x", 104, int.class),
    RESOURCE_NAME_Y("resource_name__y", 38, int.class),
    RESOURCE_NAME_FONT_SIZE("resource_name__font_size", 14, int.class),
    RESOURCE_NAME_BOLD("resource_name__bold", false, boolean.class),
    RESOURCE_NAME_TEXT_ALIGN("resource_name__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    RESOURCE_NAME_FONT_FACE("resource_name__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    LIKES_X("likes__x", 104, int.class),
    LIKES_Y("likes__y", 55, int.class),
    LIKES_FONT_SIZE("likes__font_size", 14, int.class),
    LIKES_BOLD("likes__bold", false, boolean.class),
    LIKES_TEXT_ALIGN("likes__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    LIKES_FONT_FACE("likes__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    DOWNLOADS_X("downloads__x", 104, int.class),
    DOWNLOADS_Y("downloads__y", 72, int.class),
    DOWNLOADS_FONT_SIZE("downloads__font_size", 14, int.class),
    DOWNLOADS_BOLD("downloads__bold", false, boolean.class),
    DOWNLOADS_TEXT_ALIGN("downloads__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    DOWNLOADS_FONT_FACE("downloads__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    REVIEWS_X("reviews__x", 104, int.class),
    REVIEWS_Y("reviews__y", 89, int.class),
    REVIEWS_FONT_SIZE("reviews__font_size", 14, int.class),
    REVIEWS_BOLD("reviews__bold", false, boolean.class),
    REVIEWS_TEXT_ALIGN("reviews__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    REVIEWS_FONT_FACE("reviews__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class);

    private final String key;
    private final Object def;
    private final Class<?> type;

    AuthorParameter(String key, Object def, Class<?> type) {
        this.key = key;
        this.def = def;
        this.type = type;
    }

    public static Map<AuthorParameter, Object> parse(Map<String, String> rawParams) {
        return ParamUtil.parse(AuthorParameter.class, rawParams);
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
