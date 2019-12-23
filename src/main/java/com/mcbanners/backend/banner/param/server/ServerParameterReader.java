package com.mcbanners.backend.banner.param.server;

import com.mcbanners.backend.banner.BannerTemplate;
import com.mcbanners.backend.banner.param.ServerTextParameterReader;
import com.mcbanners.backend.banner.param.TextParameterReader;

import java.util.Map;

public class ServerParameterReader {
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final TextParameterReader servNameParams;
    private final TextParameterReader verNameParams;
    private final TextParameterReader motdNameParams;
    private final TextParameterReader playerCountParams;

    public ServerParameterReader(Map<ServerParameter, Object> parameters) {
        this.template = (BannerTemplate) parameters.get(ServerParameter.TEMPLATE);
        this.logoSize = (int) parameters.get(ServerParameter.LOGO_SIZE);
        this.logoX = (int) parameters.get(ServerParameter.LOGO_X);
        this.servNameParams = new ServerTextParameterReader("serv_name", parameters);
        this.verNameParams = new ServerTextParameterReader("ver_name", parameters);
        this.motdNameParams = new ServerTextParameterReader("motd_name", parameters);
        this.playerCountParams = new ServerTextParameterReader("player_count", parameters);
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

    public TextParameterReader getServNameParams() {
        return servNameParams;
    }

    public TextParameterReader getVerNameParams() {
        return verNameParams;
    }

    public TextParameterReader getMotdNameParams() {
        return motdNameParams;
    }

    public TextParameterReader getPlayerCountParams() {
        return playerCountParams;
    }
}
