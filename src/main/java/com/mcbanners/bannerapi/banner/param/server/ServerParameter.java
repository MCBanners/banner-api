package com.mcbanners.bannerapi.banner.param.server;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.banner.BannerTextAlign;

import java.util.HashMap;
import java.util.Map;

public enum ServerParameter {
    TEMPLATE("template", BannerTemplate.MOONLIGHT_PURPLE, BannerTemplate.class),
    LOGO_SIZE("logo_size", 80, int.class),
    LOGO_X("logo_x", 12, int.class),
    SERV_NAME_X("serv_name_x", 104, int.class),
    SERV_NAME_Y("serv_name_y", 22, int.class),
    SERV_NAME_FONT_SIZE("serv_name_font_size", 18, int.class),
    SERV_NAME_BOLD("serv_name_bold", true, boolean.class),
    SERV_NAME_TEXT_ALIGN("serv_name_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    SERV_NAME_FONT_FACE("serv_name_font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    SERV_NAME_DISPLAY("serv_name_display", "", String.class),
    VER_NAME_X("ver_name_x", 104, int.class),
    VER_NAME_Y("ver_name_y", 38, int.class),
    VER_NAME_FONT_SIZE("ver_name_font_size", 14, int.class),
    VER_NAME_BOLD("ver_name_bold", false, boolean.class),
    VER_NAME_TEXT_ALIGN("ver_name_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    VER_NAME_FONT_FACE("ver_name_font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    MOTD_NAME_X("motd_name_x", 104, int.class),
    MOTD_NAME_Y("motd_name_y", 55, int.class),
    MOTD_NAME_FONT_SIZE("motd_name_font_size", 14, int.class),
    MOTD_NAME_BOLD("motd_name_bold", false, boolean.class),
    MOTD_NAME_TEXT_ALIGN("motd_name_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    MOTD_NAME_FONT_FACE("motd_name_font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    PLAYER_COUNT_X("player_count_x", 104, int.class),
    PLAYER_COUNT_Y("player_count_y", 85, int.class),
    PLAYER_COUNT_FONT_SIZE("player_count_font_size", 14, int.class),
    PLAYER_COUNT_BOLD("player_count_bold", false, boolean.class),
    PLAYER_COUNT_TEXT_ALIGN("player_count_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    PLAYER_COUNT_FONT_FACE("player_count_font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class);

    private final String key;
    private final Object def;
    private final Class<?> type;

    ServerParameter(String key, Object def, Class<?> type) {
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

    public static Map<ServerParameter, Object> parse(Map<String, String> params) {
        Map<ServerParameter, Object> processed = new HashMap<>();

        for (ServerParameter parameter : values()) {
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

    public static ServerParameter fromKey(String key) {
        try {
            return ServerParameter.valueOf(key.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
