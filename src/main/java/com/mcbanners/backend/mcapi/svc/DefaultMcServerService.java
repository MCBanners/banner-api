package com.mcbanners.backend.mcapi.svc;

<<<<<<< Updated upstream
public class DefaultMcServerService {
=======
import com.mcbanners.backend.mcapi.McAPIClient;
import com.mcbanners.backend.obj.McServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"server"})
public class DefaultMcServerService implements McServerService {
    private final McAPIClient client;

    @Autowired
    public DefaultMcServerService(McAPIClient client) {
        this.client = client;
    }

    @Cacheable
    @Override
    public McServer getServer(String host) {
        McServer server = loadServer(host);
        return new McServer(server.getHost(), server.getPort(), server.getVersion(), server.getPlayers(), server.getMotd(), server.getIcon());
    }

    @Cacheable
    @Override
    public McServer getServer(String host, int port) {
        McServer server = loadServer(host, port);
        return new McServer(server.getHost(), server.getPort(), server.getVersion(), server.getPlayers(), server.getMotd(), server.getIcon());
    }

    private McServer loadServer(String host) {
        ResponseEntity<McServer> server = client.getServer(host);
        if (server == null) {
            return null;
        }
        else {
            return server.getBody();
        }
    }

    private McServer loadServer(String host, int port) {
        ResponseEntity<McServer> server = client.getServer(host, port);
        if (server == null) {
            return null;
        }
        else {
            return server.getBody();
        }
    }
>>>>>>> Stashed changes
}
