package com.mcbanners.backend.img.layout;

import com.mcbanners.backend.banner.BannerSprite;
import com.mcbanners.backend.banner.param.TextParameterReader;
import com.mcbanners.backend.banner.param.server.ServerParameter;
import com.mcbanners.backend.banner.param.server.ServerParameterReader;
import com.mcbanners.backend.img.ImageBuilder;
import com.mcbanners.backend.img.component.Component;
import com.mcbanners.backend.img.component.ImageComponent;
import com.mcbanners.backend.obj.McServer;
import com.mcbanners.backend.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class ServerLayout extends Layout {
    private final McServer server;
    private final ServerParameterReader parameters;

    public ServerLayout(McServer server, Map<ServerParameter, Object> parameters) {
        this.server = server;

        this.parameters = new ServerParameterReader(parameters);
    }

    public List<Component> getComponents() {
        List<Component> components = new ArrayList<>();
        Color textColor = getTextColor(parameters.getTemplate());

        try {
            BufferedImage serverLogo = BannerSprite.DEFAULT_SPIGOT_RES_LOGO.getImage();

            String logo = server.getIcon();
            if (!logo.isEmpty()) {
                BufferedImage temp = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(server.getIcon())));
                if (temp != null) {
                    serverLogo = temp;
                }
            }

            if (serverLogo == null) {
                throw new RuntimeException("Could not load real or fallback logo for banner, giving up");
            }

            int serverLogoSize = Math.min(parameters.getLogoSize(), 96);
            if (serverLogoSize < 96) {
                serverLogo = ImageUtil.resize(serverLogo, serverLogoSize, serverLogoSize);
            }

            components.add(new ImageComponent(
                    parameters.getLogoX(),
                    (100 - serverLogoSize) / 2,
                    serverLogo
            ));
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
        }

        TextParameterReader name = parameters.getServNameParams();
        components.add(name.makeComponent(textColor, server.getHost()));

        TextParameterReader version = parameters.getVerNameParams();
        components.add(version.makeComponent(textColor, server.getVersion()));

        TextParameterReader motd = parameters.getMotdNameParams();
        components.add(motd.makeComponent(textColor, server.getMotd()));

        TextParameterReader players = parameters.getPlayerCountParams();
        components.add(players.makeComponent(textColor, String.format("%s / %s players online", server.getPlayers().getOnline(), server.getPlayers().getMax())));

        return components;
    }

    @Override
    public BufferedImage draw() {
        ImageBuilder builder = ImageBuilder.create(parameters.getTemplate().getImage());

        for (Component component : getComponents()) {
            builder = component.draw(builder);
        }

        return builder.build();
    }
}
