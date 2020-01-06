package com.mcbanners.bannerapi.banner.param;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.util.ParamUtil;

import java.util.Map;

public enum ResourceParameter implements BannerParameter<Object> {
    RESOURCE_NAME_X("resource_name__x", 104, int.class),
    RESOURCE_NAME_Y("resource_name__y", 25, int.class),
    RESOURCE_NAME_FONT_SIZE("resource_name__font_size", 18, int.class),
    RESOURCE_NAME_BOLD("resource_name__bold", true, boolean.class),
    RESOURCE_NAME_TEXT_ALIGN("resource_name__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    RESOURCE_NAME_FONT_FACE("resource_name__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    RESOURCE_NAME_DISPLAY("resource_name__display", "UNSET", String.class),
    AUTHOR_NAME_X("author_name__x", 104, int.class),
    AUTHOR_NAME_Y("author_name__y", 42, int.class),
    AUTHOR_NAME_FONT_SIZE("author_name__font_size", 14, int.class),
    AUTHOR_NAME_BOLD("author_name__bold", false, boolean.class),
    AUTHOR_NAME_TEXT_ALIGN("author_name__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    AUTHOR_NAME_FONT_FACE("author_name__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    REVIEWS_X("reviews__x", 104, int.class),
    REVIEWS_Y("reviews__y", 62, int.class),
    REVIEWS_FONT_SIZE("reviews__font_size", 14, int.class),
    REVIEWS_BOLD("reviews__bold", false, boolean.class),
    REVIEWS_TEXT_ALIGN("reviews__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    REVIEWS_FONT_FACE("reviews__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    STARS_X("stars__x", 180, int.class),
    STARS_Y("stars__y", 51, int.class),
    STARS_GAP("stars__gap", 16.0, double.class),
    DOWNLOADS_X("downloads__x", 104, int.class),
    DOWNLOADS_Y("downloads__y", 83, int.class),
    DOWNLOADS_FONT_SIZE("downloads__font_size", 14, int.class),
    DOWNLOADS_BOLD("downloads__bold", false, boolean.class),
    DOWNLOADS_TEXT_ALIGN("downloads__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    DOWNLOADS_FONT_FACE("downloads__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    PRICE_X("price__x", 210, int.class),
    PRICE_Y("price__y", 83, int.class),
    PRICE_FONT_SIZE("price__font_size", 14, int.class),
    PRICE_BOLD("price__bold", true, boolean.class),
    PRICE_TEXT_ALIGN("price__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    PRICE_FONT_FACE("price__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class);

    private final String key;
    private final Object def;
    private final Class<?> type;

    ResourceParameter(String key, Object def, Class<?> type) {
        this.key = key;
        this.def = def;
        this.type = type;
    }

    public static Map<ResourceParameter, Object> parse(Map<String, String> params) {
        return ParamUtil.parse(ResourceParameter.class, params);
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
