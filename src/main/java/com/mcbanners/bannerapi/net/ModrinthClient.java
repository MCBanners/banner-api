package com.mcbanners.bannerapi.net;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthResource;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthUser;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.Collections;

@Component
public class ModrinthClient extends BasicHttpClient {
    public ModrinthClient() {
        super("https://api.modrinth.com/v2/");
    }

    public final ResponseEntity<ModrinthResource> getResource(String name) {
        try {
            return get("project/" + name, ModrinthResource.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Modrinth Resource by id %s: %s", name, ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }

    public final ResponseEntity<ArrayNode> getMainProjectAuthor(String authorId) {
        try {
            return get("project/" + authorId + "/members", ArrayNode.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Modrinth Team by teamId %s: %s", authorId, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public final ResponseEntity<ModrinthUser> getUserInformation(String username) {
        try {
            return get("user/" + username, ModrinthUser.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Modrinth User by username %s: %s", username, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public final ResponseEntity<ArrayNode> getUserProjects(String username) {
        try {
            return get("user/" + username + "/projects", ArrayNode.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Modrinth User by username %s: %s", username, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public final ResponseEntity<byte[]> getResourceIcon(String url) {
        try {
            return get(url, "", byte[].class, headers -> {
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));
                return headers;
            });
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Modrinth Resource Icon by url %s: %s", url, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
