package com.mcbanners.bannerapi.net;


import com.mcbanners.bannerapi.obj.backend.mcmarket.MCMarketAuthor;
import com.mcbanners.bannerapi.obj.backend.mcmarket.MCMarketResource;
import com.mcbanners.bannerapi.obj.backend.mcmarket.MCMarketResourceBasic;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.Collections;

@Component
public class MCMarketClient extends BasicHttpClient {

    @Value("${mcmarket.key}")
    private String key;

    public MCMarketClient() {
        super("https://api.mc-market.org/v1/");
    }

    public final ResponseEntity<MCMarketResource> getResource(int id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Private " + this.key);
            return get("resources/" + id, MCMarketResource.class, httpHeaders -> headers);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to get resource " + id + " from MC-Market", ex);
            ex.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<MCMarketResourceBasic> getAllByAuthor(int id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Private " + this.key);
            return get("resources/authors/" + id, MCMarketResourceBasic.class, httpHeaders -> headers);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load all Spigot Resources by author id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public final ResponseEntity<MCMarketAuthor> getAuthor(int id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Private " + this.key);
            return get("members/" + id, MCMarketAuthor.class, httpHeaders -> headers);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to get author " + id + " from MC-Market", ex);
            ex.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<byte[]> getResourceIcon(String url) {
        try {
            return get(url, "", byte[].class, headers -> {
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));
                return headers;
            });
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load MCMarket Resource Icon by url %s: %s", url, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
