package com.mcbanners.bannerapi.util;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.banner.param.BannerParameter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParamUtil {
    public static <T extends BannerParameter<Object>> T fromKey(Class<T> enumConst, String namespace, String name) {
        return fromKey(enumConst, String.format("%s__%s", namespace, name));
    }

    public static <T extends BannerParameter<Object>> T fromKey(Class<T> enumConst, String key) {
        return Arrays.stream(enumConst.getEnumConstants()).filter(e -> e.getKey().equalsIgnoreCase(key)).findFirst().orElse(null);
    }

    public static <T extends BannerParameter<Object>> Map<T, Object> parse(Class<T> enumConst, Map<String, String> rawParams) {
        Map<T, Object> processed = new HashMap<>();

        for (T parameter : enumConst.getEnumConstants()) {
            String value = rawParams.get(parameter.getKey());

            Object out = parameter.getDefault();
            if (value != null) {
                out = convertRawText(parameter.getType(), value);
            }

            processed.put(parameter, out);
        }

        return processed;
    }

    public static Object convertRawText(Class<?> clazz, String value) {
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
}
