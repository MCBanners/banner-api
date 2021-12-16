package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.ore.OreAuthor;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.Collections;

@Component
public class OreClient extends BasicHttpClient {
    private static final String IMAGE_BASE_URL = "https://ore.spongepowered.org/";

    public OreClient() {
        super("https://ore.spongepowered.org/api/v1/");
    }

    public final ResponseEntity<OreAuthor> getAuthor(String authorId) {
        try {
            return get("users/" + authorId, OreAuthor.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Ore Author by authorId %s: %s", authorId, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public final ResponseEntity<OreResource> getResource(String pluginId) {
        try {
            return get("projects/" + pluginId, OreResource.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Ore Resource by pluginId %s: %s", pluginId, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public final ResponseEntity<byte[]> getResourceIcon(String href) {
        if (href.startsWith("/")) {
            href = href.substring(1);
        }

        return getImage(IMAGE_BASE_URL + href + "/icon");
    }

    public final ResponseEntity<byte[]> getAuthApiImage(String link) {
        return getImage(link.trim());
    }

    public final ResponseEntity<byte[]> getImage(String url) {
        try {
            return get(url, "", byte[].class, headers -> {
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));
                return headers;
            });
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Ore Image from URL %s: %s", url, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
