package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.polymart.PolyMartAuthor;
import com.mcbanners.bannerapi.obj.backend.polymart.PolyMartResource;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.Collections;

@Component
public class PolyMartClient extends BasicHttpClient {
    public PolyMartClient() {
        super("https://api.polymart.org/v1/");
    }

    public ResponseEntity<PolyMartResource> getResource(int id) {
        try {
            return get(String.format("getResourceInfo/?resource_id=%d", id), PolyMartResource.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load PolyMart Resource by id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<PolyMartAuthor> getAuthor(int id) {
        try {
            return get(String.format("getAccountInfo/?user_id=%d", id), PolyMartAuthor.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load PolyMart Author by id %d: %s", id, ex.getMessage());
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
