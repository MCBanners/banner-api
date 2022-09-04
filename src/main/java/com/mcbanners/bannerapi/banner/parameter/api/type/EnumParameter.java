package com.mcbanners.bannerapi.banner.parameter.api.type;

import com.mcbanners.bannerapi.util.EnumUtil;

public class EnumParameter<T extends Enum<T>> extends Parameter<T> {
    public EnumParameter(String key, Class<T> enumClass) {
        this(key, enumClass, null);
    }

    public EnumParameter(String key, Class<T> enumClass, T defaultValue) {
        super(key, enumClass, defaultValue, str -> EnumUtil.stringToConst(str, enumClass));
    }
}
