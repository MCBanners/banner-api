package com.mcbanners.bannerapi.banner.param;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.util.ParamUtil;

import java.util.Map;

public enum DiscordParameter implements BannerParameter<Object> {
    DISCORD_NAME_X("discord_name__x", 104, int.class),
    DISCORD_NAME_Y("discord_name__y", 22, int.class),
    DISCORD_NAME_FONT_SIZE("discord_name__font_size", 18, int.class),
    DISCORD_NAME_BOLD("discord_name__bold", true, boolean.class),
    DISCORD_NAME_TEXT_ALIGN("discord_name__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    DISCORD_NAME_FONT_FACE("discord_name__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    DISCORD_NAME_DISPLAY("discord_name__display", "", String.class),

    ID_X("id__x", 104, int.class),
    ID_Y("id__y", 38, int.class),
    ID_FONT_SIZE("id__font_size", 14, int.class),
    ID_BOLD("id__bold", false, boolean.class),
    ID_TEXT_ALIGN("id__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    ID_FONT_FACE("id__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),

    STATUS_X("status__x", 104, int.class),
    STATUS_Y("status__y", 55, int.class),
    STATUS_FONT_SIZE("status__font_size", 14, int.class),
    STATUS_BOLD("status__bold", false, boolean.class),
    STATUS_TEXT_ALIGN("status__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    STATUS_FONT_FACE("status__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),

    ACTIVITY_X("activity__x", 104, int.class),
    ACTIVITY_Y("activity__y", 72, int.class),
    ACTIVITY_FONT_SIZE("activity__font_size", 14, int.class),
    ACTIVITY_BOLD("activity__bold", false, boolean.class),
    ACTIVITY_TEXT_ALIGN("activity__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    ACTIVITY_FONT_FACE("activity__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),

    CREATED_X("created__x", 104, int.class),
    CREATED_Y("created__y", 89, int.class),
    CREATED_FONT_SIZE("created__font_size", 14, int.class),
    CREATED_BOLD("created__bold", false, boolean.class),
    CREATED_TEXT_ALIGN("created__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    CREATED_FONT_FACE("created__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class);

    private final String key;
    private final Object def;
    private final Class<?> type;

    DiscordParameter(String key, Object def, Class<?> type) {
        this.key = key;
        this.def = def;
        this.type = type;
    }

    public static Map<DiscordParameter, Object> parse(Map<String, String> params) {
        return ParamUtil.parse(DiscordParameter.class, params);
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
