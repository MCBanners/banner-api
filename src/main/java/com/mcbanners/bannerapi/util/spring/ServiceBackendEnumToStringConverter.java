package com.mcbanners.bannerapi.util.spring;

import com.mcbanners.bannerapi.service.ServiceBackend;
import org.springframework.core.convert.converter.Converter;

public class ServiceBackendEnumToStringConverter implements Converter<String, ServiceBackend> {
    @Override
    public ServiceBackend convert(final String source) {
        try {
            return ServiceBackend.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
