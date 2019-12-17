package com.mcbanners.backend.img.layout;

import com.mcbanners.backend.banner.BannerTemplate;
import com.mcbanners.backend.banner.BannerTextTheme;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Layout {
    private final Color light = new Color(230, 224, 224);
    private final Color dark = new Color(65, 60, 60);

    public Color getTextColor(BannerTemplate template) {
        return template.getTextTheme() == BannerTextTheme.LIGHT ? light : dark;
    }

    public abstract BufferedImage draw();
}
