package com.mcbanners.bannerapi.banner.param;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.util.ParamUtil;

import java.util.Map;

public enum ServerParameter implements BannerParameter<Object> {
    SERVER_NAME_X("server_name__x", 104, int.class),
    SERVER_NAME_Y("server_name__y", 22, int.class),
    SERVER_NAME_FONT_SIZE("server_name__font_size", 18, int.class),
    SERVER_NAME_BOLD("server_name__bold", true, boolean.class),
    SERVER_NAME_TEXT_ALIGN("server_name__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    SERVER_NAME_FONT_FACE("server_name__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    SERVER_NAME_DISPLAY("server_name__display", "UNSET", String.class),
    VERSION_X("version__x", 104, int.class),
    VERSION_Y("version__y", 38, int.class),
    VERSION_FONT_SIZE("version__font_size", 14, int.class),
    VERSION_BOLD("version__bold", false, boolean.class),
    VERSION_TEXT_ALIGN("version__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    VERSION_FONT_FACE("version__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    MOTD_X("motd__x", 104, int.class),
    MOTD_Y("motd__y", 55, int.class),
    MOTD_FONT_SIZE("motd__font_size", 14, int.class),
    MOTD_BOLD("motd__bold", false, boolean.class),
    MOTD_TEXT_ALIGN("motd__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    MOTD_FONT_FACE("motd__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class),
    PLAYERS_X("players__x", 104, int.class),
    PLAYERS_Y("players__y", 85, int.class),
    PLAYERS_FONT_SIZE("players__font_size", 14, int.class),
    PLAYERS_BOLD("players__bold", false, boolean.class),
    PLAYERS_TEXT_ALIGN("players__text_align", BannerTextAlign.LEFT, BannerTextAlign.class),
    PLAYERS_FONT_FACE("players__font_face", BannerFontFace.SOURCE_SANS_PRO, BannerFontFace.class);

    private final String key;
    private final Object def;
    private final Class<?> type;

    ServerParameter(String key, Object def, Class<?> type) {
        this.key = key;
        this.def = def;
        this.type = type;
    }

    public static Map<ServerParameter, Object> parse(Map<String, String> params) {
        return ParamUtil.parse(ServerParameter.class, params);
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
