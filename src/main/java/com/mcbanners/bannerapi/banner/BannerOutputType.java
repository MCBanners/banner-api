package com.mcbanners.bannerapi.banner;

import java.util.Arrays;

public enum BannerOutputType {
    PNG("png"),
    JPEG("jpg");

    private final String name;

    BannerOutputType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BannerOutputType getByName(String name) {
        return Arrays.stream(values()).filter(v -> v.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
