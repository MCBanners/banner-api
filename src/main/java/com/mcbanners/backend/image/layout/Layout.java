package com.mcbanners.backend.image.layout;

import com.mcbanners.backend.banner.BannerTemplate;
import com.mcbanners.backend.banner.BannerTextTheme;
import com.mcbanners.backend.image.component.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Layout {
    private static final Color light = new Color(230, 224, 224);
    private static final Color dark = new Color(65, 60, 60);
    private final List<Component> components = new ArrayList<>();

    public final void addComponent(Component component) {
        components.add(component);
    }

    public final List<Component> getComponents() {
        return components;
    }

    public Color getTextColor(BannerTemplate template) {
        return template.getTextTheme() == BannerTextTheme.LIGHT ? light : dark;
    }

    public abstract List<Component> build();

    public abstract BufferedImage draw();
}
