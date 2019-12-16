package com.mcbanners.backend.banner;

public enum BannerTextAlign {
    RIGHT,
    CENTER,
    LEFT;

    public static BannerTextAlign fromString(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
