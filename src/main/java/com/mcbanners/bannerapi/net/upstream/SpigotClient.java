package com.mcbanners.bannerapi.net.upstream;

import com.mcbanners.bannerapi.net.BasicHttpClient;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotAuthor;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotResource;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
public final class SpigotClient extends BasicHttpClient {
    public SpigotClient() {
        super("https://api.spigotmc.org/simple/0.2/index.php?action=");
    }

    public ResponseEntity<SpigotAuthor> getAuthor(int id) {
        try {
            return get(String.format("getAuthor&id=%d", id), SpigotAuthor.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Spigot Author by id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<SpigotResource> getResource(int id) {
        try {
            return get(String.format("getResource&id=%d", id), SpigotResource.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Spigot Resource by id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<SpigotResource[]> getAllByAuthor(int id) {
        try {
            return get(String.format("getResourcesByAuthor&id=%d", id), SpigotResource[].class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load all Spigot Resources by author id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
