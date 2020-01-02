package com.mcbanners.bannerapi.service.api;

import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;

public interface MinecraftServerService {
    MinecraftServer getServer(final String host, final int port);
}
