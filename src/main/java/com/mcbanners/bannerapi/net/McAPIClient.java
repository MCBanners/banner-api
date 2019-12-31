package com.mcbanners.backend.net;

import com.mcbanners.backend.obj.backend.mcapi.MinecraftServer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public final class McAPIClient extends BasicHttpClient {
    public McAPIClient() {
        super("http://localhost:8083/server/");
    }

    public final ResponseEntity<MinecraftServer> getServer(String host, int port) {
        return get(String.format("?host=%s&port=%d", host, port), MinecraftServer.class);
    }
}
