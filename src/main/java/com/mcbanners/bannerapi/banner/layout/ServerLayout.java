package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.banner.parameter.ServerParameters;
import com.mcbanners.bannerapi.banner.parameter.api.namespace.TextParameterNamespace;
import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;

import java.util.List;
import java.util.Map;

public class ServerLayout extends Layout<ServerParameters> {
    private final MinecraftServer server;
    private final String serverTitle;

    public ServerLayout(MinecraftServer server, Map<String, String> rawParameters) {
        super(new ServerParameters(rawParameters));

        this.server = server;

        String serverTitle = parameters().getServerName().readDisplay();
        if (serverTitle.isEmpty() || serverTitle.equalsIgnoreCase("unset")) {
            serverTitle = server.host();
        }

        this.serverTitle = serverTitle;
    }

    @Override
    public List<BasicComponent> build() {
        component(new LogoComponent(
                parameters().getLogo().readX(),
                parameters().getLogo().readSize(),
                server.icon(),
                Sprite.DEFAULT_SERVER_LOGO
        ));

        text(parameters().getServerName(), serverTitle);

        text(parameters().getVersion(), server.version());

        TextParameterNamespace motd = parameters().getMotd();
        if (motd.readEnable()) {
            wrappingText(motd, motd.readMaxCharacters(), server.formattedMotd());
        }

        text(parameters().getPlayers(), "%s / %s players online", server.onlinePlayers(), server.maxPlayers());

        return components();
    }
}
