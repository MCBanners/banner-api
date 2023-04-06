package com.mcbanners.bannerapi.net.upstream;

import com.mcbanners.bannerapi.net.BasicHttpClient;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthResource;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthUser;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

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

    public final ResponseEntity<ModrinthUser> getMainProjectAuthor(String projectId) {
        try {
            return get("project/" + projectId + "/members", ModrinthUser.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Modrinth Team by projectId %s: %s", projectId, ex.getMessage());
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

    public final ResponseEntity<ModrinthResource[]> getUserProjects(String username) {
        try {
            return get("user/" + username + "/projects", ModrinthResource[].class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Modrinth User by username %s: %s", username, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
