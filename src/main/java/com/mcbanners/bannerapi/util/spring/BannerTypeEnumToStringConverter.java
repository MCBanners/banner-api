package com.mcbanners.bannerapi.util.spring;

import com.mcbanners.bannerapi.banner.BannerType;
import io.sentry.Sentry;
import org.springframework.core.convert.converter.Converter;

public class BannerTypeEnumToStringConverter implements Converter<String, BannerType> {
    @Override
    public BannerType convert(String from) {
        try {
            return BannerType.valueOf(from.toUpperCase());
        } catch (IllegalArgumentException ex) {
            Sentry.captureException(ex);
            return null;
        }
    }
}
