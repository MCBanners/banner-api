package com.mcbanners.bannerapi.image.layout;

import com.mcbanners.bannerapi.banner.BannerSprite;
import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.banner.param.ParameterReader;
import com.mcbanners.bannerapi.banner.param.ServerParameter;
import com.mcbanners.bannerapi.banner.param.TextParameterReader;
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
    private final String serverTitle;
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final TextParameterReader<ServerParameter> serverName;
    private final TextParameterReader<ServerParameter> version;
    private final boolean enableMotd;
    private final int motdMaxChars;
    private final TextParameterReader<ServerParameter> motd;
    private final TextParameterReader<ServerParameter> players;

    public ServerLayout(MinecraftServer server, Map<String, String> parameters) {
        this.server = server;

        ParameterReader<ServerParameter> reader = new ParameterReader<>(ServerParameter.class, parameters);
        reader.addTextReaders("server_name", "version", "motd", "players");

        String serverTitle = (String) reader.getOrDefault(ServerParameter.SERVER_NAME_DISPLAY);
        if (serverTitle.isEmpty() || serverTitle.equalsIgnoreCase("unset")) {
            serverTitle = server.getHost();
        }

        this.serverTitle = serverTitle;
        template = reader.getBannerTemplate();
        logoSize = reader.getLogoSize();
        logoX = reader.getLogoX();
        serverName = reader.getTextReader("server_name");
        version = reader.getTextReader("version");
        enableMotd = (boolean) reader.getOrDefault(ServerParameter.MOTD_ENABLE);
        motdMaxChars = (int) reader.getOrDefault(ServerParameter.MOTD_MAX_CHARS);
        motd = reader.getTextReader("motd");
        players = reader.getTextReader("players");
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(template);

        addComponent(new LogoComponent(logoX, BannerSprite.DEFAULT_SERVER_LOGO, server.getIcon(), logoSize));
        addComponent(serverName.makeComponent(textColor, serverTitle));
        addComponent(version.makeComponent(textColor, server.getVersion()));

        if (enableMotd) {
            addComponent(motd.makeComponent(textColor, server.getMotd().getFormatted(), true, motdMaxChars));
        }

        addComponent(players.makeComponent(textColor, String.format("%s / %s players online", server.getPlayers().getOnline(), server.getPlayers().getMax())));

        return getComponents();
    }

    @Override
    public BufferedImage draw() {
        ImageBuilder builder = ImageBuilder.create(template.getImage());

        for (BasicComponent component : build()) {
            builder = component.draw(builder);
        }

        return builder.build();
    }
}
