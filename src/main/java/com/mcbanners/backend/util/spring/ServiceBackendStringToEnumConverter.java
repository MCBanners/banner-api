package com.mcbanners.backend.util.spring;

import com.mcbanners.backend.service.ServiceBackend;
import org.springframework.core.convert.converter.Converter;

public class ServiceBackendStringToEnumConverter implements Converter<String, ServiceBackend> {
    @Override
    public ServiceBackend convert(String from) {
        try {
            return ServiceBackend.valueOf(from.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
