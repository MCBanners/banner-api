package com.mcbanners.bannerapi.util;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.banner.param.BannerParameter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    @SuppressWarnings("unchecked")
    public static <T extends BannerParameter<Object>> Map<String, Object> makeMap(Class<T> enumConst) {
        Map<String, TemporaryObjectHolder> mapped = Arrays.stream(enumConst.getEnumConstants())
                .collect(Collectors.toMap(BannerParameter::getKey, a -> new TemporaryObjectHolder(a.getType(), a.getDefault())));

        Map<String, Object> out = new HashMap<>();
        for (Map.Entry<String, TemporaryObjectHolder> entry : mapped.entrySet()) {
            String[] nameSplit = entry.getKey().split("__");
            String namespace = nameSplit[0];

            TemporaryObjectHolder value = entry.getValue();
            Object resolvedValue = value.object.getClass().isAssignableFrom(value.clazz) ? value.clazz.cast(value.object) : value.object;

            Object insert = resolvedValue;
            if (nameSplit.length > 1) {
                String name = nameSplit[1];
                Map<String, Object> target = out.containsKey(namespace) ? (Map<String, Object>) out.get(namespace) : new HashMap<>();
                target.put(name, resolvedValue);
                insert = target;
            }

            out.put(namespace, insert);
        }

        return out;
    }

    private static class TemporaryObjectHolder {
        private final Class<?> clazz;
        private final Object object;

        public TemporaryObjectHolder(Class<?> clazz, Object object) {
            this.clazz = clazz;
            this.object = object;
        }
    }
}
