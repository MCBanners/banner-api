package com.mcbanners.bannerapi.banner.param;

import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.util.ParamUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParameterReader<T extends BannerParameter<Object>> {
    private final Class<T> enumConst;
    private final Map<String, String> parameters;
    private final Map<T, Object> parsedParameters;
    private final Map<String, TextParameterReader<T>> textReaders;

    public ParameterReader(Class<T> enumConst, Map<String, String> parameters) {
        this.enumConst = enumConst;
        this.parameters = parameters;
        this.parsedParameters = ParamUtil.parse(enumConst, parameters);
        this.textReaders = new HashMap<>();
    }

    public final BannerTemplate getBannerTemplate() {
        return (BannerTemplate) getOrDefault(GeneralParameter.TEMPLATE);
    }

    public final int getLogoSize() {
        return (int) getOrDefault(GeneralParameter.LOGO_SIZE);
    }

    public final int getLogoX() {
        return (int) getOrDefault(GeneralParameter.LOGO_X);
    }

    public final void addTextReaders(String... namespaces) {
        Arrays.stream(namespaces).forEach(this::addTextReader);
    }

    public final void addTextReader(String namespace) {
        this.textReaders.put(namespace, new TextParameterReader<>(namespace, parsedParameters, enumConst));
    }

    public final TextParameterReader<T> getTextReader(String namespace) {
        return this.textReaders.get(namespace);
    }

    public final <U> Object getOrDefault(BannerParameter<U> parameter) {
        return getOrDefault(parameter.getKey(), parameter.getType(), parameter.getDefault());
    }

    public final <U> Object getOrDefault(String key, Class<? extends U> clazz, U def) {
        String value = parameters.get(key);

        Object out = def;
        if (value != null) {
            out = ParamUtil.convertRawText(clazz, value);
        }

        return out;
    }
}
