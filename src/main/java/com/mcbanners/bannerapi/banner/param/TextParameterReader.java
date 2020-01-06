package com.mcbanners.bannerapi.banner.param;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.image.component.TextComponent;
import com.mcbanners.bannerapi.util.ParamUtil;

import java.awt.*;
import java.util.Map;

public class TextParameterReader<T extends BannerParameter<Object>> {
    protected final String namespace;
    protected final Map<T, Object> params;
    protected final Class<T> enumConst;

    public TextParameterReader(String namespace, Map<T, Object> params, Class<T> enumConst) {
        this.namespace = namespace;
        this.params = params;
        this.enumConst = enumConst;
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

    public final TextComponent makeComponent(Color textColor, String content) {
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

    private T var(String name) {
        return ParamUtil.fromKey(enumConst, namespace, name);
    }
}

