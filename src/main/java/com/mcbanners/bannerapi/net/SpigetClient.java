package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.spiget.SpigetAuthor;
import com.mcbanners.bannerapi.obj.backend.spiget.SpigetResource;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
public final class SpigetClient extends BasicHttpClient {
    public SpigetClient() {
        super("https://api.spiget.org/v2/");
    }

    public ResponseEntity<SpigetResource> getResource(int id) {
        try {
            return get("resources/" + id, SpigetResource.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Spiget Resource by id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<SpigetAuthor> getAuthor(int id) {
        try {
            return get("authors/" + id, SpigetAuthor.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Spiget Author by id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
