package com.mcbanners.bannerapi.banner.parameter.api.type;

public class IntegerParameter extends Parameter<Integer> {
    public IntegerParameter(String key) {
        this(key, null);
    }

    public IntegerParameter(String key, Integer defaultValue) {
        super(key, Integer.class, defaultValue, Integer::parseInt);
    }
}
