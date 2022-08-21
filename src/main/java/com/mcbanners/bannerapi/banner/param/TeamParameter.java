package com.mcbanners.bannerapi.banner.param;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.util.ParamUtil;

import java.util.Map;

public enum TeamParameter implements BannerParameter<Object> {
    TEAM_NAME_X("team_name__x", 104, int.class),
    TEAM_NAME_Y("team_name__y", 22, int.class),
    TEAM_NAME_FONT_SIZE("team_name__font_size", 18, int.class),
    TEAM_NAME_BOLD("team_name__bold", true, boolean.class),
    TEAM_NAME_TEXT_ALIGN("team_name__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    TEAM_NAME_FONT_FACE("team_name__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    RESOURCE_COUNT_X("resource_count__x", 104, int.class),
    RESOURCE_COUNT_Y("resource_count__y", 38, int.class),
    RESOURCE_COUNT_FONT_SIZE("resource_count__font_size", 14, int.class),
    RESOURCE_COUNT_BOLD("resource_count__bold", false, boolean.class),
    RESOURCE_COUNT_TEXT_ALIGN("resource_count__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    RESOURCE_COUNT_FONT_FACE("resource_count__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    DOWNLOADS_X("downloads__x", 104, int.class),
    DOWNLOADS_Y("downloads__y", 72, int.class),
    DOWNLOADS_FONT_SIZE("downloads__font_size", 14, int.class),
    DOWNLOADS_BOLD("downloads__bold", false, boolean.class),
    DOWNLOADS_TEXT_ALIGN("downloads__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    DOWNLOADS_FONT_FACE("downloads__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    RATINGS_X("ratings__x", 104, int.class),
    RATINGS_Y("ratings__y", 89, int.class),
    RATINGS_FONT_SIZE("ratings__font_size", 14, int.class),
    RATINGS_BOLD("ratings__bold", false, boolean.class),
    RATINGS_TEXT_ALIGN("ratings__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    RATINGS_FONT_FACE("ratings__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class);

    private final String key;
    private final Object def;
    private final Class<?> type;

    TeamParameter(String key, Object def, Class<?> type) {
        this.key = key;
        this.def = def;
        this.type = type;
    }

    public static Map<TeamParameter, Object> parse(Map<String, String> rawParams) {
        return ParamUtil.parse(TeamParameter.class, rawParams);
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
