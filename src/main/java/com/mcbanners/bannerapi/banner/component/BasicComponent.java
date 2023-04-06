package com.mcbanners.bannerapi.banner.component;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.ImageBuilder;

public abstract class BasicComponent {
    protected final int x;
    protected final int y;

    public BasicComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract ImageBuilder draw(ImageBuilder builder, BannerOutputFormat outputType);
}
