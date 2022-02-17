package com.mcbanners.bannerapi.net;

import com.fasterxml.jackson.databind.JsonNode;
import com.mcbanners.bannerapi.obj.backend.ore.OreAuthor;
import com.mcbanners.bannerapi.obj.backend.ore.OreAuthorization;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Component
public class OreClient extends BasicHttpClient {
    private static final String IMAGE_BASE_URL = "https://auth.spongepowered.org/avatar/";

    private Instant expiration;
    private String session;

    public OreClient() {
        super("https://ore.spongepowered.org/api/v2/");
        auth();
    }

    public final ResponseEntity<OreAuthorization> initialAuthProcess() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.ACCEPT, "application/json");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            return post("authenticate", OreAuthorization.class, httpHeaders -> headers);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to Authenticate to Ore API");
            ex.printStackTrace();
            if (ex.getRawStatusCode() == 401) {
                auth();
            }
            return null;
        }
    }

    public void auth() {
        ResponseEntity<OreAuthorization> resp = initialAuthProcess();
        if (resp == null) {
            return;
        }

        OreAuthorization auth = resp.getBody();
        this.expiration = Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(auth.getExpires()));
        this.session = auth.getSession();
    }

    public final ResponseEntity<OreAuthor> getAuthor(String authorId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "OreApi session=" + this.session);
            return get("users/" + authorId, OreAuthor.class, httpHeaders -> headers);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Ore Author by authorId %s: %s", authorId, ex.getMessage());
            ex.printStackTrace();
            if (ex.getRawStatusCode() == 401) {
                auth();
            }
            return null;
        }
    }

    public final ResponseEntity<JsonNode> getProjectsFromAuthor(String authorId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "OreApi session=" + this.session);
            return get("projects?owner=" + authorId, JsonNode.class, httpHeaders -> headers);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Ore Author by authorId %s: %s", authorId, ex.getMessage());
            ex.printStackTrace();
            if (ex.getRawStatusCode() == 401) {
                auth();
            }
            return null;
        }
    }

    public final ResponseEntity<OreResource> getResource(String pluginId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "OreApi session=" + this.session);
            return get("projects/" + pluginId, OreResource.class, httpHeaders -> headers);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Ore Resource by pluginId %s: %s", pluginId, ex.getMessage());
            ex.printStackTrace();
            if (ex.getRawStatusCode() == 401) {
                auth();
            }
            return null;
        }
    }

    public final ResponseEntity<byte[]> getAuthorIcon(String href) {
        if (href.startsWith("/")) {
            href = href.substring(1);
        }

        return getImage(IMAGE_BASE_URL + href + "?size=120x120");
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

    public Instant getExpiration() {
        return expiration;
    }

    public String getSession() {
        return session;
    }
}
