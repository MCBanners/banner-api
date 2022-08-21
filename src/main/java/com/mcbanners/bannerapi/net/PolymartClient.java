package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.polymart.PolymartAuthor;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartResource;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartTeam;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.Collections;

@Component
public class PolymartClient extends BasicHttpClient {
    public PolymartClient() {
        super("https://api.polymart.org/v1/");
    }

    public ResponseEntity<PolymartResource> getResource(int id) {
        try {
            return get(String.format("getResourceInfo/?resource_id=%d", id), PolymartResource.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load PolyMart Resource by id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<PolymartAuthor> getAuthor(int id) {
        try {
            return get(String.format("getAccountInfo/?user_id=%d", id), PolymartAuthor.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load PolyMart Author by id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<PolymartTeam> getTeam(int id) {
        try {
            return get(String.format("getAccountInfo/?team_id=%d", id), PolymartTeam.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load PolyMart Team by id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<byte[]> getIcon(String url) {
        try {
            return get(url, "", byte[].class, headers -> {
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));
                return headers;
            });
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load PolyMart Icon by url %s: %s", url, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
