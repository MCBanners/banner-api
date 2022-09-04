package com.mcbanners.bannerapi.banner.parameter.api.namespace;

import com.mcbanners.bannerapi.banner.parameter.api.type.IntegerParameter;
import com.mcbanners.bannerapi.banner.parameter.api.type.Parameter;

import java.util.HashMap;
import java.util.Map;

public class OrientableParameterNamespace extends ParameterNamespace {
    private final Parameter<Integer> x;
    private final Parameter<Integer> y;

    public OrientableParameterNamespace(String namespace, Map<String, String> rawParameters) {
        super(namespace, rawParameters);
        this.x = new IntegerParameter("x", 0);
        this.y = new IntegerParameter("y", 0);
    }

    public final Parameter<Integer> getX() {
        return x;
    }

    public final Integer readX() {
        return read(x);
    }

    public final Parameter<Integer> getY() {
        return y;
    }

    public final Integer readY() {
        return read(y);
    }

    public Map<String, Object> defaults() {
        Map<String, Object> output = new HashMap<>();

        output.put("x", getX().defaultValue());
        output.put("y", getY().defaultValue());

        return output;
    }
}
