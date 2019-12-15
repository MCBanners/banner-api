package com.mcbanners.backend.banner;

public enum BannerTextAlign {
    RIGHT,
    CENTER,
    LEFT;

    public static BannerTextAlign fromString(String name) {
        return valueOf(name.toUpperCase());
    }
}