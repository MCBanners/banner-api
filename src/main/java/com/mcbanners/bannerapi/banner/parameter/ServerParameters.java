package com.mcbanners.bannerapi.banner.parameter;

import com.mcbanners.bannerapi.banner.parameter.api.namespace.TextParameterNamespace;

import java.util.Map;

public class ServerParameters extends GlobalParameters {
    private final TextParameterNamespace serverName;
    private final TextParameterNamespace version;
    private final TextParameterNamespace motd;
    private final TextParameterNamespace players;

    public ServerParameters(Map<String, String> rawParameters) {
        super(rawParameters);

        serverName = new TextParameterNamespace("server_name", rawParameters);
        version = new TextParameterNamespace("version", rawParameters);
        motd = new TextParameterNamespace("motd", rawParameters);
        players = new TextParameterNamespace("players", rawParameters);

        // Server Name defaults
        serverName.getX().defaultValue(104);
        serverName.getY().defaultValue(22);
        serverName.getFontSize().defaultValue(18);
        serverName.getFontBold().defaultValue(true);

        // Version defaults
        version.getX().defaultValue(104);
        version.getY().defaultValue(38);

        // MOTD defaults
        motd.getX().defaultValue(104);
        motd.getY().defaultValue(55);

        // Players defaults
        players.getX().defaultValue(104);
        players.getY().defaultValue(85);
    }

    public TextParameterNamespace getServerName() {
        return serverName;
    }

    public TextParameterNamespace getVersion() {
        return version;
    }

    public TextParameterNamespace getMotd() {
        return motd;
    }

    public TextParameterNamespace getPlayers() {
        return players;
    }

    @Override
    public Map<String, Map<String, Object>> defaults() {
        Map<String, Map<String, Object>> output = super.defaults();

        output.put("server_name", serverName.defaults());
        output.put("version", version.defaults());
        output.put("motd", motd.defaults());
        output.put("players", players.defaults());

        return output;
    }
}
