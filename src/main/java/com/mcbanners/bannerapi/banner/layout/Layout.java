package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.BackgroundTemplate;
import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.TextTheme;
import com.mcbanners.bannerapi.banner.component.BasicComponent;

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

    public Color getTextColor(BackgroundTemplate template) {
        return template.getTextTheme() == TextTheme.LIGHT ? light : dark;
    }

    public abstract List<BasicComponent> build();

    public abstract BufferedImage draw(BannerOutputFormat outputType);
}
