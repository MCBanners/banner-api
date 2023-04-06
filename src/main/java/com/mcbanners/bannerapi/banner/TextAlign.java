package com.mcbanners.bannerapi.banner;

public enum TextAlign {
    RIGHT,
    CENTER,
    LEFT;

    public static TextAlign fromString(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
