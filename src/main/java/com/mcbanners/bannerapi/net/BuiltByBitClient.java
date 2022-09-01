package com.mcbanners.bannerapi.net;


import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitAuthor;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitMember;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResource;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResourceBasic;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.Collections;

@Component
public class BuiltByBitClient extends BasicHttpClient {

    @Value("${builtbybit.key}")
    private String key;

    public BuiltByBitClient() {
        super("https://api.builtbybit.com/v1/");
    }

    public final ResponseEntity<BuiltByBitResource> getResource(int id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Private " + this.key);
            return get("resources/" + id, BuiltByBitResource.class, httpHeaders -> headers);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to get resource " + id + " from BuiltByBit", ex);
            ex.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<BuiltByBitResourceBasic> getAllByAuthor(int id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Private " + this.key);
            return get("resources/authors/" + id, BuiltByBitResourceBasic.class, httpHeaders -> headers);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load all BuiltByBit Resources by author id %d: %s", id, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public final ResponseEntity<BuiltByBitAuthor> getAuthor(int id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Private " + this.key);
            return get("members/" + id, BuiltByBitAuthor.class, httpHeaders -> headers);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to get author " + id + " from BuiltByBit", ex);
            ex.printStackTrace();
        }

        return null;
    }

    public final ResponseEntity<BuiltByBitMember> getMember(int id) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Private " + this.key);
            return get("members/" + id, BuiltByBitMember.class, httpHeaders -> headers);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to get member " + id + " from BuiltByBit", ex);
            ex.printStackTrace();
        }

        return null;
    }
}
