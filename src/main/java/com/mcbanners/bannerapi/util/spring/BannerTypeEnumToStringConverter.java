package com.mcbanners.bannerapi.util.spring;

import com.mcbanners.bannerapi.banner.BannerType;
import com.mcbanners.bannerapi.service.ServiceBackend;
import org.springframework.core.convert.converter.Converter;

public class BannerTypeEnumToStringConverter implements Converter<String, BannerType> {
    @Override
    public BannerType convert(String from) {
        try {
            return BannerType.valueOf(from.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
