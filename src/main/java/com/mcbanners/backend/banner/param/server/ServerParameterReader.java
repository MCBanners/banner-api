package com.mcbanners.backend.banner.param.server;

import com.mcbanners.backend.banner.BannerTemplate;

import java.util.Map;

public class ServerParameterReader {
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final ServerTextParameterReader servNameParams;
    private final ServerTextParameterReader verNameParams;
    private final ServerTextParameterReader motdNameParams;
    private final ServerTextParameterReader playerCountParams;

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

    public ServerTextParameterReader getServNameParams() {
        return servNameParams;
    }

    public ServerTextParameterReader getVerNameParams() {
        return verNameParams;
    }

    public ServerTextParameterReader getMotdNameParams() {
        return motdNameParams;
    }

    public ServerTextParameterReader getPlayerCountParams() {
        return playerCountParams;
    }
}
