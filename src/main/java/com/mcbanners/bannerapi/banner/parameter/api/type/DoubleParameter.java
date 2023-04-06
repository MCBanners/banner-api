package com.mcbanners.bannerapi.banner.parameter.api.type;

public class DoubleParameter extends Parameter<Double> {
    public DoubleParameter(String key) {
        this(key, null);
    }

    public DoubleParameter(String key, Double defaultValue) {
        super(key, Double.class, defaultValue, Double::parseDouble);
    }
}
