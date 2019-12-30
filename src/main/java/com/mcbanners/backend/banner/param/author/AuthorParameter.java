package com.mcbanners.backend.banner.param.author;

import com.mcbanners.backend.banner.BannerFontFace;
import com.mcbanners.backend.banner.BannerTemplate;
import com.mcbanners.backend.banner.BannerTextAlign;

import java.util.HashMap;
import java.util.Map;

public enum AuthorParameter {
    TEMPLATE("template", BannerTemplate.MOONLIGHT_PURPLE, BannerTemplate.class),
    LOGO_SIZE("logo_size", 80, int.class),
    LOGO_X("logo_x", 12, int.class),
    AUT_NAME_X("aut_name_x", 104, int.class),
    AUT_NAME_Y("aut_name_y", 22, int.class),
    AUT_NAME_FONT_SIZE("aut_name_font_size", 18, int.class),
    AUT_NAME_BOLD("aut_name_bold", true, boolean.class),
    AUT_NAME_TEXT_ALIGN("aut_name_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    AUT_NAME_FONT_FACE("aut_name_font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    RES_COUNT_X("res_count_x", 104, int.class),
    RES_COUNT_Y("res_count_y", 38, int.class),
    RES_COUNT_FONT_SIZE("res_count_font_size", 14, int.class),
    RES_COUNT_BOLD("res_count_bold", false, boolean.class),
    RES_COUNT_TEXT_ALIGN("res_count_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    RES_COUNT_FONT_FACE("res_count_font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    DL_COUNT_X("dl_count_x", 104, int.class),
    DL_COUNT_Y("dl_count_y", 89, int.class),
    DL_COUNT_FONT_SIZE("dl_count_font_size", 14, int.class),
    DL_COUNT_BOLD("dl_count_bold", false, boolean.class),
    DL_COUNT_TEXT_ALIGN("dl_count_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    DL_COUNT_FONT_FACE("dl_count_font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    LIKES_COUNT_X("likes_count_x", 104, int.class),
    LIKES_COUNT_Y("likes_count_y", 55, int.class),
    LIKES_COUNT_FONT_SIZE("likes_count_font_size", 14, int.class),
    LIKES_COUNT_BOLD("likes_count_bold", false, boolean.class),
    LIKES_COUNT_TEXT_ALIGN("likes_count_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    LIKES_COUNT_FONT_FACE("likes_count_font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    REV_COUNT_X("rev_count_x", 104, int.class),
    REV_COUNT_Y("rev_count_y", 72, int.class),
    REV_COUNT_FONT_SIZE("rev_count_font_size", 14, int.class),
    REV_COUNT_BOLD("rev_count_bold", false, boolean.class),
    REV_COUNT_TEXT_ALIGN("rev_count_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    REV_COUNT_FONT_FACE("rev_count_font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class);

    private final String key;
    private final Object def;
    private final Class<?> type;

    AuthorParameter(String key, Object def, Class<?> type) {
        this.key = key;
        this.def = def;
        this.type = type;
    }

    public static Map<AuthorParameter, Object> parse(Map<String, String> params) {
        Map<AuthorParameter, Object> processed = new HashMap<>();

        for (AuthorParameter parameter : values()) {
            String value = params.get(parameter.getKey());

            Object out = parameter.getDefault();
            if (value != null) {
                out = convert(parameter.getType(), value);
            }

            processed.put(parameter, out);
        }


        return processed;
    }

    public static Object convert(Class<?> clazz, String value) {
        switch (clazz.getSimpleName()) {
            case "BannerTemplate":
                return BannerTemplate.fromString(value);
            case "BannerTextAlign":
                return BannerTextAlign.fromString(value);
            case "BannerFontFace":
                return BannerFontFace.fromString(value);
            case "String":
                return value;
            case "int":
                return Integer.parseInt(value);
            case "double":
                return Double.parseDouble(value);
            case "boolean":
                return Boolean.parseBoolean(value);
        }

        return null;
    }

    public static AuthorParameter fromKey(String key) {
        try {
            return AuthorParameter.valueOf(key.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public String getKey() {
        return key;
    }

    public Object getDefault() {
        return def;
    }

    public Class<?> getType() {
        return type;
    }
}
