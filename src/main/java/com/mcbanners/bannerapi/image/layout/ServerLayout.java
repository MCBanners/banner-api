package com.mcbanners.bannerapi.image.layout;

import com.mcbanners.bannerapi.banner.BannerSprite;
import com.mcbanners.bannerapi.banner.param.server.ServerParameter;
import com.mcbanners.bannerapi.banner.param.server.ServerParameterReader;
import com.mcbanners.bannerapi.banner.param.server.ServerTextParameterReader;
import com.mcbanners.bannerapi.image.ImageBuilder;
import com.mcbanners.bannerapi.image.component.BasicComponent;
import com.mcbanners.bannerapi.image.component.LogoComponent;
import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class ServerLayout extends Layout {
    private final MinecraftServer server;
    private final ServerParameterReader parameters;
    private final String serverName;

    public ServerLayout(MinecraftServer server, Map<ServerParameter, Object> parameters) {
        this.server = server;

        String serverName = (String) parameters.get(ServerParameter.SERV_NAME_DISPLAY);
        if (serverName.isEmpty()) {
            serverName = server.getHost();
        }

        this.serverName = serverName;

        this.parameters = new ServerParameterReader(parameters);
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(parameters.getTemplate());

        addComponent(new LogoComponent(parameters.getLogoX(), BannerSprite.DEFAULT_SERVER_LOGO, server.getIcon(), parameters.getLogoSize()));

        ServerTextParameterReader name = parameters.getServNameParams();
        addComponent(name.makeComponent(textColor, serverName));

        ServerTextParameterReader version = parameters.getVerNameParams();
        addComponent(version.makeComponent(textColor, server.getVersion()));

        ServerTextParameterReader motd = parameters.getMotdNameParams();
        addComponent(motd.makeComponent(textColor, server.getMotd().getRaw()));

        ServerTextParameterReader players = parameters.getPlayerCountParams();
        addComponent(players.makeComponent(textColor, String.format("%s / %s players online", server.getPlayers().getOnline(), server.getPlayers().getMax())));

        return getComponents();
    }

    @Override
    public BufferedImage draw() {
        ImageBuilder builder = ImageBuilder.create(parameters.getTemplate().getImage());

        for (BasicComponent component : build()) {
            builder = component.draw(builder);
        }

        return builder.build();
    }
}
