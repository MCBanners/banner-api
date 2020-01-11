package com.mcbanners.bannerapi.util.spring;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class BannerOutputTypeEnumToStringConverter implements Converter<String, BannerOutputType> {
    @Override
    public BannerOutputType convert(@NonNull String from) {
        return BannerOutputType.getByName(from);
    }
}
