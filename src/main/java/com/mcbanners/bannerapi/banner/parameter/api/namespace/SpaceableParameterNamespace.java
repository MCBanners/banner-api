package com.mcbanners.bannerapi.banner.parameter.api.namespace;

import com.mcbanners.bannerapi.banner.parameter.api.type.DoubleParameter;
import com.mcbanners.bannerapi.banner.parameter.api.type.Parameter;

import java.util.Map;

public class SpaceableParameterNamespace extends OrientableParameterNamespace {
    private final Parameter<Double> gap;

    public SpaceableParameterNamespace(String namespace, Map<String, String> rawParameters) {
        super(namespace, rawParameters);
        this.gap = new DoubleParameter("gap", 16.0);
    }

    public Parameter<Double> getGap() {
        return gap;
    }

    public Double readGap() {
        return read(gap);
    }

    @Override
    public Map<String, Object> defaults() {
        Map<String, Object> output = super.defaults();

        output.put("gap", getGap().defaultValue());

        return output;
    }
}
