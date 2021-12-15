package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.ore.OreAuthor;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
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
            return null;
        }
    }

    public final ResponseEntity<OreResource> getResource(String pluginId) {
        try {
            return get("projects/" + pluginId, OreResource.class);
        } catch (RestClientResponseException ex) {
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
            return null;
        }
    }
}
