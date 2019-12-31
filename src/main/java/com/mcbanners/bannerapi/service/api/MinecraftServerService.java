package com.mcbanners.backend.service.api;

import com.mcbanners.backend.obj.backend.mcapi.MinecraftServer;

public interface MinecraftServerService {
    MinecraftServer getServer(final String host, final int port);
}
