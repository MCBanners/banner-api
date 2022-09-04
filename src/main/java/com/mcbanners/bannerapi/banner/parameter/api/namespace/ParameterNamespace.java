package com.mcbanners.bannerapi.banner.parameter.api.namespace;

import com.mcbanners.bannerapi.banner.parameter.api.type.Parameter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ParameterNamespace {
    private static final String NAMESPACE_KEY_SEPARATOR = "__";
    private final String namespace;
    private final Map<String, String> rawParameters;

    public ParameterNamespace(String namespace, Map<String, String> rawParameters) {
        this.namespace = namespace;

        if (rawParameters == null) {
            this.rawParameters = Collections.emptyMap();
        } else {
            this.rawParameters = rawParameters.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith(String.format("%s%s", namespace, NAMESPACE_KEY_SEPARATOR)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

    public String namespace() {
        return this.namespace;
    }

    public final String namespacedKey(Parameter<?> parameter) {
        return namespacedKey(parameter.key());
    }

    public final String namespacedKey(String key) {
        if (namespace == null || namespace.isEmpty()) {
            return key;
        } else {
            return String.join(NAMESPACE_KEY_SEPARATOR, namespace, key);
        }
    }

    public final <T> T read(Parameter<T> parameter) {
        Map.Entry<String, String> rawParameter = rawParameters.entrySet().stream()
                .filter(entry -> entry.getKey().equals(namespacedKey(parameter)))
                .findFirst().orElse(null);

        if (rawParameter == null) {
            if (parameter.hasDefaultValue()) {
                return parameter.defaultValue();
            } else {
                return null;
            }
        }

        return parameter.read(rawParameter.getValue());
    }
}
