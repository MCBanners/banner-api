package com.mcbanners.bannerapi.image.component;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.image.ImageBuilder;

public abstract class BasicComponent {
    protected final int x;
    protected final int y;

    public BasicComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract ImageBuilder draw(ImageBuilder builder, BannerOutputType outputType);
}
