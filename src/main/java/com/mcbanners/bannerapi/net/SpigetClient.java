package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.spiget.SpigetAuthor;
import com.mcbanners.bannerapi.obj.backend.spiget.SpigetResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public final class SpigetClient extends BasicHttpClient {
    public SpigetClient() {
        super("https://api.spiget.org/v2/");
    }

    public final ResponseEntity<SpigetAuthor> getAuthor(int id) {
        return get(String.format("authors/%d", id), SpigetAuthor.class);
    }

    public final ResponseEntity<SpigetResource> getResource(int id) {
        return get(String.format("resources/%d", id), SpigetResource.class);
    }

    public final ResponseEntity<SpigetResource[]> getAllByAuthor(int id) {
        return getAllByAuthor(id, 300);
    }

    public final ResponseEntity<SpigetResource[]> getAllByAuthor(int id, int size) {
        return get(String.format("authors/%d/resources?size=%d", id, size), SpigetResource[].class);
    }
}
