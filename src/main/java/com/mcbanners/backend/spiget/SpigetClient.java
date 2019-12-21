package com.mcbanners.backend.spiget;

import com.mcbanners.backend.obj.spiget.SpigetAuthor;
import com.mcbanners.backend.obj.spiget.SpigetResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SpigetClient {
    private static final String BASE_URL = "https://api.spiget.org/v2/";
    private static final String USER_AGENT = "SpigotBanners/0.0.1-SNAPSHOT";

    public ResponseEntity<SpigetAuthor> getAuthor(int id) {
        return makeRequest("authors/" + id, SpigetAuthor.class);
    }

    public ResponseEntity<SpigetResource> getResource(int id) {
        return makeRequest("resources/" + id, SpigetResource.class);
    }

    public ResponseEntity<SpigetAuthor> getAllByAuthor(int id) {
        return makeRequest("authors/" + id + "/resources", SpigetAuthor.class);
    }

    private <T> ResponseEntity<T> makeRequest(String endpoint, Class<T> type) {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", USER_AGENT);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        try {
            return template.exchange(
                    BASE_URL + endpoint,
                    HttpMethod.GET,
                    entity,
                    type
            );
        } catch (Exception ex) {
            return null;
        }
    }
}
