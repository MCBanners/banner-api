package com.mcbanners.backend.banner;

import java.util.HashMap;
import java.util.Map;

public enum BannerParameter {
    TEMPLATE("template", BannerTemplate.MOONLIGHT_PURPLE, BannerTemplate.class),
    LOGO_SIZE("logo_size", 80, int.class),
    LOGO_X("logo_x", 12, int.class),
    RES_NAME_X("res_name_x", 104, int.class),
    RES_NAME_Y("res_name_y", 25, int.class),
    RES_NAME_FONT_SIZE("res_name_font_size", 18, int.class),
    RES_NAME_BOLD("res_name_bold", true, boolean.class),
    RES_NAME_TEXT_ALIGN("res_name_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    RES_NAME_FONT("res_name_font", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    AUT_NAME_X("aut_name_x", 104, int.class),
    AUT_NAME_Y("aut_name_y", 42, int.class),
    AUT_NAME_FONT_SIZE("aut_name_font_size", 14, int.class),
    AUT_NAME_BOLD("aut_name_bold", false, boolean.class),
    AUT_NAME_TEXT_ALIGN("aut_name_text_align",  BannerTextAlign.LEFT, BannerTextAlign.class),
    AUT_NAME_FONT("aut_name_font", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    REV_COUNT_X("rev_count_x", 104, int.class),
    REV_COUNT_Y("rev_count_y", 62, int.class),
    REV_COUNT_FONT_SIZE("rev_count_font_size", 14, int.class),
    REV_COUNT_BOLD("rev_count_bold", false, boolean.class),
    REV_COUNT_TEXT_ALIGN("rev_count_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    REV_COUNT_FONT("rev_count_font", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    STARS_X("stars_x", 180, int.class),
    STARS_Y("stars_y", 51, int.class),
    STARS_GAP("stars_gap", 16.0, double.class),
    DL_COUNT_X("dl_count_x", 104, int.class),
    DL_COUNT_Y("dl_count_y", 83, int.class),
    DL_COUNT_FONT_SIZE("dl_count_font_size", 14, int.class),
    DL_COUNT_BOLD("dl_count_bold", false, boolean.class),
    DL_COUNT_TEXT_ALIGN("dl_count_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    DL_COUNT_FONT("dl_count_font", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    PRICE_X("price_x", 210, int.class),
    PRICE_Y("price_y", 83, int.class),
    PRICE_FONT_SIZE("price_font_size", 14, int.class),
    PRICE_BOLD("price_bold", true, boolean.class),
    PRICE_TEXT_ALIGN("price_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    PRICE_FONT("price_font", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class);

    private final String key;
    private final Object def;
    private final Class<?> type;

    BannerParameter(String key, Object def, Class<?> type) {
        this.key = key;
        this.def = def;
        this.type = type;
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

    public static Map<BannerParameter, Object> parse(Map<String, String> params) {
        Map<BannerParameter, Object> processed = new HashMap<>();

        for (BannerParameter parameter : values()) {
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
            case "int":
                return Integer.parseInt(value);
            case "double":
                return Double.parseDouble(value);
            case "boolean":
                return Boolean.parseBoolean(value);
        }

        return null;
    }
}
