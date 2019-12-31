package com.mcbanners.backend.image.layout;

import com.mcbanners.backend.banner.BannerSprite;
import com.mcbanners.backend.banner.param.server.ServerParameter;
import com.mcbanners.backend.banner.param.server.ServerParameterReader;
import com.mcbanners.backend.banner.param.server.ServerTextParameterReader;
import com.mcbanners.backend.image.ImageBuilder;
import com.mcbanners.backend.image.component.Component;
import com.mcbanners.backend.image.component.LogoComponent;
import com.mcbanners.backend.obj.backend.mcapi.MinecraftServer;

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
    public List<Component> build() {
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

        for (Component component : build()) {
            builder = component.draw(builder);
        }

        return builder.build();
    }
}
