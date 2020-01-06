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

    public Integer getX() {
        return get("x", Integer.class);
    }

    public Integer getY() {
        return get("y", Integer.class);
    }

    public Integer getFontSize() {
        return get("font_size", Integer.class);
    }

    public Boolean getBold() {
        return get("bold", Boolean.class);
    }

    public BannerTextAlign getTextAlign() {
        return get("text_align", BannerTextAlign.class);
    }

    public BannerFontFace getFontFace() {
        return get("font_face", BannerFontFace.class);
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

    private <U> U get(String name, Class<U> type) {
        T var = var(name);
        Object found = params.get(var);
        Object toReturn = found == null ? var.getDefault() : found;
        return type.cast(toReturn);
    }

    private T var(String name) {
        return ParamUtil.fromKey(enumConst, namespace, name);
    }
}

