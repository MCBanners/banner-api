package com.mcbanners.bannerapi.banner.parameter.api.type;

public class BooleanParameter extends Parameter<Boolean> {
    public BooleanParameter(String key) {
        this(key, null);
    }

    public BooleanParameter(String key, Boolean defaultValue) {
        super(key, Boolean.class, defaultValue, Boolean::parseBoolean);
    }
}
