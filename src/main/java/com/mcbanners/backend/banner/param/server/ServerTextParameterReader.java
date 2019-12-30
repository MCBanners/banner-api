package com.mcbanners.backend.banner.param.server;

import com.mcbanners.backend.banner.BannerFontFace;
import com.mcbanners.backend.banner.BannerTextAlign;
import com.mcbanners.backend.banner.param.TextParameterReader;

import java.util.Map;

public class ServerTextParameterReader extends TextParameterReader<ServerParameter> {
    public ServerTextParameterReader(String namespace, Map<ServerParameter, Object> params) {
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

    private ServerParameter var(String name) {
        return ServerParameter.fromKey(String.format("%s_%s", namespace, name));
    }
}
