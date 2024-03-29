package com.mcbanners.bannerapi.net.upstream;

import com.mcbanners.bannerapi.net.BasicHttpClient;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartAuthor;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartResource;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
public class PolymartClient extends BasicHttpClient {
    public PolymartClient() {
        super("https://api.polymart.org/v1/");
    }

    public ResponseEntity<PolymartResource> getResource(int id) {
        try {
            return get(String.format("getResourceInfo/?resource_id=%d", id), PolymartResource.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Polymart Resource by id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<PolymartAuthor> getAuthor(int id) {
        try {
            return get(String.format("getAccountInfo/?user_id=%d", id), PolymartAuthor.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Polymart Author by id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<PolymartAuthor> getTeam(int id) {
        try {
            return get(String.format("getAccountInfo/?team_id=%d", id), PolymartAuthor.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Polymart Team by id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
