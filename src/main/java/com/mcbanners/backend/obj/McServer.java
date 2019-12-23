package com.mcbanners.backend.obj;

<<<<<<< Updated upstream
public class Server {
=======
public class McServer {
    private final String host;
    private final int port;
    private final String version;
    private final McPlayerStats players;
    private final String motd;
    private final String icon;

    public McServer(String host, int port, String version, McPlayerStats players, String motd, String icon) {
        this.host = host;
        this.port = port;
        this.version = version;
        this.players = players;
        this.motd = motd;
        this.icon = icon;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getVersion() {
        return version;
    }

    public McPlayerStats getPlayers() {
        return players;
    }

    public String getMotd() {
        return motd;
    }

    public String getIcon() {
        return icon;
    }
>>>>>>> Stashed changes
}
