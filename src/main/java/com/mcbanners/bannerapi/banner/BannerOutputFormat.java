package com.mcbanners.bannerapi.banner;

import java.util.Arrays;

public enum BannerOutputFormat {
    PNG("png"),
    JPEG("jpg");

    private final String name;

    BannerOutputFormat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BannerOutputFormat getByName(String name) {
        return Arrays.stream(values()).filter(v -> v.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
