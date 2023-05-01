package com.mcbanners.bannerapi.net.upstream;

import com.fasterxml.jackson.databind.JsonNode;
import com.mcbanners.bannerapi.net.BasicHttpClient;
import com.mcbanners.bannerapi.obj.backend.ore.OreAuthor;
import com.mcbanners.bannerapi.obj.backend.ore.OreAuthorization;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.time.Instant;
import java.util.Locale;

@Component
public class OreClient extends BasicHttpClient {
    private static final String IMAGE_BASE_URL = "https://auth.spongepowered.org/avatar/";
    private OreAuthorization authorization;
    private boolean priorAuthorizationFailed;

    public OreClient() {
        super("https://ore.spongepowered.org/api/v2/");
        authorize();
    }

    public final ResponseEntity<OreAuthor> getAuthor(String authorId) {
        try {
            return get(String.format("users/%s", authorId), OreAuthor.class, this::injectAuthorization);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Ore Author by authorId %s: %s", authorId, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public final ResponseEntity<JsonNode> getProjectsFromAuthor(String authorId) {
        try {
            return get(String.format("projects?owner=%s", authorId), JsonNode.class, this::injectAuthorization);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Ore Author by authorId %s: %s", authorId, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public final ResponseEntity<OreResource> getResource(String pluginId) {
        try {
            // Fun fact - apparently resource name lowercase = ore's plugin id, so let's just lowercase it for them
            // just in case...
            return get(String.format("projects/%s", pluginId.toLowerCase(Locale.ROOT)), OreResource.class, this::injectAuthorization);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Ore Resource by pluginId %s: %s", pluginId, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public final String getAuthorIcon(String authorName) {
        if (authorName.startsWith("/")) {
            authorName = authorName.substring(1);
        }

        return getBase64Image(IMAGE_BASE_URL + authorName + "?size=120x120");
    }

    private void authorize() {
        if (this.authorization == null || Instant.now().isAfter(this.authorization.expires())) {
            try {
                authorization = post("authenticate", OreAuthorization.class).getBody();
                priorAuthorizationFailed = false;
            } catch (RestClientResponseException ex) {
                Log.error("Failed to Authenticate to Ore API");
                ex.printStackTrace();

                if (ex.getStatusCode().value() == 401 && !priorAuthorizationFailed) {
                    Log.warn("Retrying authentication process...");
                    priorAuthorizationFailed = true;
                    authorize();
                }
            }
        }
    }

    private HttpHeaders injectAuthorization(HttpHeaders headers) {
        authorize();

        if (this.authorization != null) {
            headers.add(HttpHeaders.AUTHORIZATION, String.format("OreApi session=%s", this.authorization.session()));
        }

        return headers;
    }
}
