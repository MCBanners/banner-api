package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.ImageBuilder;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.banner.parameter.ServerParameters;
import com.mcbanners.bannerapi.banner.parameter.api.namespace.TextParameterNamespace;
import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class ServerLayout extends Layout {
    private final MinecraftServer server;
    private final ServerParameters serverParameters;
    private final String serverTitle;

    public ServerLayout(MinecraftServer server, Map<String, String> rawParameters) {
        this.server = server;
        this.serverParameters = new ServerParameters(rawParameters);

        String serverTitle = serverParameters.getServerName().readDisplay();
        if (serverTitle.isEmpty() || serverTitle.equalsIgnoreCase("unset")) {
            serverTitle = server.host();
        }

        this.serverTitle = serverTitle;
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(serverParameters.getBackground().readTemplate());

        addComponent(new LogoComponent(
                serverParameters.getLogo().readX(),
                serverParameters.getLogo().readSize(),
                server.icon(),
                Sprite.DEFAULT_SERVER_LOGO
        ));

        addComponent(serverParameters.getServerName().asTextComponent(textColor, serverTitle));

        addComponent(serverParameters.getVersion().asTextComponent(textColor, server.version()));

        TextParameterNamespace motd = serverParameters.getMotd();
        if (motd.readEnable()) {
            addComponent(motd.asTextComponent(textColor, server.formattedMotd(), true, motd.readMaxCharacters()));
        }

        addComponent(serverParameters.getPlayers().asTextComponent(textColor, String.format("%s / %s players online", server.onlinePlayers(), server.maxPlayers())));

        return getComponents();
    }

    @Override
    public BufferedImage draw(BannerOutputFormat outputType) {
        ImageBuilder builder = ImageBuilder.create(serverParameters.getBackground().readTemplate().getImage(), outputType);

        for (BasicComponent component : build()) {
            builder = component.draw(builder, outputType);
        }

        return builder.build();
    }
}
