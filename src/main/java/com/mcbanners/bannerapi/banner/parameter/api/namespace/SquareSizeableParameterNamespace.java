package com.mcbanners.bannerapi.banner.parameter.api.namespace;

import com.mcbanners.bannerapi.banner.parameter.api.type.IntegerParameter;
import com.mcbanners.bannerapi.banner.parameter.api.type.Parameter;

import java.util.Map;

public class SquareSizeableParameterNamespace extends OrientableParameterNamespace {
    private final Parameter<Integer> size;

    public SquareSizeableParameterNamespace(String namespace, Map<String, String> rawParameters) {
        super(namespace, rawParameters);
        this.size = new IntegerParameter("size", 32);
    }

    public Parameter<Integer> getSize() {
        return size;
    }

    public Integer readSize() {
        return read(size);
    }

    @Override
    public Map<String, Object> defaults() {
        Map<String, Object> output = super.defaults();

        output.put("size", getSize().defaultValue());

        return output;
    }
}
