package com.mcbanners.backend.obj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class McServer {
    private String host;
    private int port;
    private String version;
    private McPlayerStats players;
    private Motd motd;
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

    public McPlayerStats getPlayers() {
        return players;
    }

    public void setPlayers(McPlayerStats players) {
        this.players = players;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Motd getMotd() {
        return motd;
    }

    public void setMotd(Motd motd) {
        this.motd = motd;
    }
}
