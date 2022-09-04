package com.mcbanners.bannerapi.util.spring;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class BannerOutputFormatEnumToStringConverter implements Converter<String, BannerOutputFormat> {
    @Override
    public BannerOutputFormat convert(@NonNull String from) {
        return BannerOutputFormat.getByName(from);
    }
}
