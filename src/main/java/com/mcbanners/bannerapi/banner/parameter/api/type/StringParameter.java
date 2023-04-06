package com.mcbanners.bannerapi.banner.parameter.api.type;

public class StringParameter extends Parameter<String> {
    public StringParameter(String key) {
        this(key, null);
    }

    public StringParameter(String key, String defaultValue) {
        super(key, String.class, defaultValue, str -> str);
    }
}
