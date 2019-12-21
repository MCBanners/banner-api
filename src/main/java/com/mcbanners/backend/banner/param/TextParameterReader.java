package com.mcbanners.backend.banner.param;

import com.mcbanners.backend.banner.BannerFontFace;
import com.mcbanners.backend.banner.BannerTextAlign;
import com.mcbanners.backend.banner.param.res.ResourceParameter;
import com.mcbanners.backend.img.component.TextComponent;

import java.awt.*;
import java.util.Map;

public class TextParameterReader<T extends Enum> {
    private final String namespace;
    private final Map<T, Object> params;

    public TextParameterReader(String namespace, Map<T, Object> params) {
        this.namespace = namespace;
        this.params = params;
    }

    public int getX() {
        return (int) params.get(var("x"));
    }

    public int getY() {
        return (int) params.get(var("y"));
    }

    public int getFontSize() {
        return (int) params.get(var("font_size"));
    }

    public boolean getBold() {
        return (boolean) params.get(var("bold"));
    }

    public BannerTextAlign getTextAlign() {
        return (BannerTextAlign) params.get(var("text_align"));
    }

    public BannerFontFace getFontFace() {
        return (BannerFontFace) params.get(var("font_face"));
    }

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

    private ResourceParameter var(String name) {
        return ResourceParameter.fromKey(String.format("%s_%s", namespace, name));
    }
}
