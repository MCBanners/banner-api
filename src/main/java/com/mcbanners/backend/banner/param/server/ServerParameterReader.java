package com.mcbanners.backend.banner.param.server;

import com.mcbanners.backend.banner.BannerTemplate;
import com.mcbanners.backend.banner.param.ServerTextParameterReader;
import com.mcbanners.backend.banner.param.TextParameterReader;

import java.util.Map;

public class ServerParameterReader {
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final TextParameterReader autNameParam;

    public ServerParameterReader(Map<ServerParameter, Object> parameters) {
        this.template = (BannerTemplate) parameters.get(ServerParameter.TEMPLATE);
        this.logoSize = (int) parameters.get(ServerParameter.LOGO_SIZE);
        this.logoX = (int) parameters.get(ServerParameter.LOGO_X);
        this.autNameParam = new ServerTextParameterReader("aut_name", parameters);
    }

    public BannerTemplate getTemplate() {
        return template;
    }

    public int getLogoSize() {
        return logoSize;
    }

    public int getLogoX() {
        return logoX;
    }

    public TextParameterReader getAutNameParam() {
        return autNameParam;
    }
}
