package com.mcbanners.bannerapi.banner.param;

public interface BannerParameter<T> {
    String getKey();

    T getDefault();

    Class<? extends T> getType();
}