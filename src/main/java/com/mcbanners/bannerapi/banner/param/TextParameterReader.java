package com.mcbanners.bannerapi.banner.param;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.image.component.TextComponent;

import java.awt.*;
import java.util.Map;

public abstract class TextParameterReader<T extends Enum<?>> {
    protected final String namespace;
    protected final Map<T, Object> params;

    public TextParameterReader(String namespace, Map<T, Object> params) {
        this.namespace = namespace;
        this.params = params;
    }

    public abstract int getX();

    public abstract int getY();

    public abstract int getFontSize();

    public abstract boolean getBold();

    public abstract BannerTextAlign getTextAlign();

    public abstract BannerFontFace getFontFace();

    public TextComponent makeComponent(Color textColor, String content) {
        return new TextComponent(
                getX(),
                getY(),
                getFontSize(),
                textColor,
                getBold(),
                getTextAlign(),
                getFontFace(),
                content
        );
    }
}

