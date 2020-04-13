package com.mcbanners.bannerapi.image.layout;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.banner.BannerTextTheme;
import com.mcbanners.bannerapi.image.component.BasicComponent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Layout {
    private static final Color light = new Color(230, 224, 224);
    private static final Color dark = new Color(65, 60, 60);
    private final List<BasicComponent> components = new ArrayList<>();

    public final void addComponent(BasicComponent component) {
        components.add(component);
    }

    public final List<BasicComponent> getComponents() {
        return components;
    }

    public Color getTextColor(BannerTemplate template) {
        return template.getTextTheme() == BannerTextTheme.LIGHT ? light : dark;
    }

    public abstract List<BasicComponent> build();

    public abstract BufferedImage draw(BannerOutputType outputType);
}
