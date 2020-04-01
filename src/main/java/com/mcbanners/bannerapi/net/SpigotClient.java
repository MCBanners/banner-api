package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.spigot.SpigotAuthor;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public final class SpigotClient extends BasicHttpClient {
    public SpigotClient() {
        super("https://api.spigotmc.org/simple/0.1/index.php?action=");
    }

    public final ResponseEntity<SpigotAuthor> getAuthor(int id) {
        return get(String.format("getAuthor&id=%d", id), SpigotAuthor.class);
    }

    public final ResponseEntity<SpigotResource> getResource(int id) {
        return get(String.format("getResource&id=%d", id), SpigotResource.class);
    }

    public final ResponseEntity<SpigotResource[]> getAllByAuthor(int id) {
        return getAllByAuthor(id, 300);
    }

    public final ResponseEntity<SpigotResource[]> getAllByAuthor(int id, int size) {
        return get(String.format("authors/%d/resources?size=%d", id, size), SpigotResource[].class);
    }
}
