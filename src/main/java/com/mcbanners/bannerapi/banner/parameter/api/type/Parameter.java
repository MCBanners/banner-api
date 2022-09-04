package com.mcbanners.bannerapi.banner.parameter.api.type;

import java.util.function.Function;

public class Parameter<T> {
    private final String key;
    private final Class<T> type;
    private final Function<String, T> transformer;

    private T defaultValue;

    public Parameter(String key, Class<T> type, Function<String, T> transformer) {
        this(key, type, null, transformer);
    }

    public Parameter(String key, Class<T> type, T defaultValue, Function<String, T> transformer) {
        this.type = type;
        this.key = key;
        this.defaultValue = defaultValue;
        this.transformer = transformer;
    }

    public final String key() {
        return key;
    }

    public final Class<T> type() {
        return type;
    }

    public final T defaultValue() {
        return defaultValue;
    }

    public final void defaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public final boolean hasDefaultValue() {
        return defaultValue() != null;
    }

    /**
     * Executes the defined {@link #transformer} on the given string to turn it into the required type {@link T}.
     * If the transformer throws an exception, the {@link #defaultValue} will be returned. If there is no default,
     * null will be returned instead.
     *
     * @param raw the raw parameter value to interpret
     * @return the transformed value (as {@link T}), the default value (if defined), or null
     */
    public final T read(String raw) {
        try {
            return transformer.apply(raw);
        } catch (Exception ignored) {
            return hasDefaultValue() ? defaultValue : null;
        }
    }
}