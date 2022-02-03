package com.mcbanners.bannerapi.banner;

import io.sentry.Sentry;

public enum BannerTextAlign {
    RIGHT,
    CENTER,
    LEFT;

    public static BannerTextAlign fromString(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ex) {
            Sentry.captureException(ex);
            return null;
        }
    }
}
