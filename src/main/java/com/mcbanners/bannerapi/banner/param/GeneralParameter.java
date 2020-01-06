package com.mcbanners.bannerapi.banner.param;

import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.util.ParamUtil;

import java.util.Map;

public enum GeneralParameter implements BannerParameter<Object> {
    TEMPLATE("template", BannerTemplate.MOONLIGHT_PURPLE, BannerTemplate.class),
    LOGO_X("logo__x", 12, int.class),
    LOGO_SIZE("logo__size", 80, int.class);

    private final String key;
    private final Object def;
    private final Class<?> type;

    GeneralParameter(String  key, Object def, Class<?> type) {
        this.key = key;
        this.def = def;
        this.type = type;
    }

    public static Map<GeneralParameter, Object> parse(Map<String, String> rawParams) {
        return ParamUtil.parse(GeneralParameter.class, rawParams);
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
