package com.mcbanners.bannerapi.util.spring;

import com.mcbanners.bannerapi.security.AuthedUserInformationMethodArgumentResolver;
import com.mcbanners.bannerapi.security.JwtHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JwtHandler jwtHandler;

    @Autowired
    public WebConfig(JwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new BannerTypeEnumToStringConverter());
        registry.addConverter(new BannerOutputTypeEnumToStringConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthedUserInformationMethodArgumentResolver(jwtHandler));
    }
}
