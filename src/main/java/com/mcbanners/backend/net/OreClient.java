package com.mcbanners.backend.net;

import com.mcbanners.backend.obj.backend.ore.OreAuthor;
import com.mcbanners.backend.obj.backend.ore.OreResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class OreClient extends BasicHttpClient {
    private static final String IMAGE_BASE_URL = "https://ore.spongepowered.org/";

    public OreClient() {
        super("https://ore.spongepowered.org/api/v1/");
    }

    public final ResponseEntity<OreAuthor> getAuthor(String authorId) {
        return get("users/" + authorId, OreAuthor.class);
    }

    public final ResponseEntity<OreResource> getResource(String pluginId) {
        return get("projects/" + pluginId, OreResource.class);
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
        return get(url, "", byte[].class, headers -> {
            headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));
            return headers;
        });
    }
}
