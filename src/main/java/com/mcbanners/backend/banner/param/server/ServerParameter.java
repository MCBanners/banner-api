package com.mcbanners.backend.banner.param.server;

import com.mcbanners.backend.banner.BannerFontFace;
import com.mcbanners.backend.banner.BannerTemplate;
import com.mcbanners.backend.banner.BannerTextAlign;

import java.util.HashMap;
import java.util.Map;

public enum ServerParameter {
    TEMPLATE("template", BannerTemplate.MOONLIGHT_PURPLE, BannerTemplate.class),
    LOGO_SIZE("logo_size", 80, int.class),
    LOGO_X("logo_x", 12, int.class),
    AUT_NAME_X("aut_name_x", 104, int.class),
    AUT_NAME_Y("aut_name_y", 42, int.class),
    AUT_NAME_FONT_SIZE("aut_name_font_size", 14, int.class),
    AUT_NAME_BOLD("aut_name_bold", false, boolean.class),
    AUT_NAME_TEXT_ALIGN("aut_name_text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    AUT_NAME_FONT_FACE("aut_name_font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class);

    private final String key;
    private final Object def;
    private final Class<?> type;

    ServerParameter(String key, Object def, Class<?> type) {
        this.key = key;
        this.def = def;
        this.type = type;
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
            return ServerParameter.valueOf(key.toLowerCase());
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