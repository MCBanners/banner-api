package com.mcbanners.bannerapi.service.api;

import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;

public interface MinecraftServerService {
    /**
     * Get a minecraft server by it's IP and port
     *
     * @param host the host of the server
     * @param port the port of the server
     * @return the generic MinecraftServer object that represents a server
     */
    MinecraftServer getServer(final String host, final int port);
}
