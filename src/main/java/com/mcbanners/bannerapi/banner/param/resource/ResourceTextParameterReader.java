package com.mcbanners.bannerapi.banner.param.resource;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.banner.param.TextParameterReader;

import java.util.Map;

public class ResourceTextParameterReader extends TextParameterReader<ResourceParameter> {
    public ResourceTextParameterReader(String namespace, Map<ResourceParameter, Object> params) {
        super(namespace, params);
    }

    @Override
    public int getX() {
        return (int) params.get(var("x"));
    }

    @Override
    public int getY() {
        return (int) params.get(var("y"));
    }

    @Override
    public int getFontSize() {
        return (int) params.get(var("font_size"));
    }

    @Override
    public boolean getBold() {
        return (boolean) params.get(var("bold"));
    }

    @Override
    public BannerTextAlign getTextAlign() {
        return (BannerTextAlign) params.get(var("text_align"));
    }

    @Override
    public BannerFontFace getFontFace() {
        return (BannerFontFace) params.get(var("font_face"));
    }

    private ResourceParameter var(String name) {
        return ResourceParameter.fromKey(String.format("%s_%s", namespace, name));
    }
}
