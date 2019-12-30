package com.mcbanners.backend.obj.backend.mcapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MinecraftServer {
    private String host;
    private int port;
    private String version;
    private MinecraftPlayerStatistics players;
    private MessageOfTheDay motd;
    private String icon;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public MinecraftPlayerStatistics getPlayers() {
        return players;
    }

    public void setPlayers(MinecraftPlayerStatistics players) {
        this.players = players;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public MessageOfTheDay getMotd() {
        return motd;
    }

    public void setMotd(MessageOfTheDay motd) {
        this.motd = motd;
    }
}
