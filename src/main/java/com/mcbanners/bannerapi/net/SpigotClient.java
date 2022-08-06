package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.spigot.SpigotAuthor;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotResource;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.Collections;

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

    public ResponseEntity<byte[]> getResourceIcon(String url) {
        try {
            return get(url, "", byte[].class, headers -> {
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));
                return headers;
            });
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Spigot Resource Icon by url %s: %s", url, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
