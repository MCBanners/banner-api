package com.mcbanners.bannerapi.util.spring;

import com.mcbanners.bannerapi.service.ServiceBackend;
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
