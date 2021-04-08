package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.hangar.HangarAuthor;
import com.mcbanners.bannerapi.obj.backend.hangar.HangarResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class HangarClient extends BasicHttpClient {
    private static final String IMAGE_BASE_URL = "https://hangar.benndorf.dev/api/internal/projects/project/";

    public HangarClient() {
        super("https://hangar.benndorf.dev/api/v1/");
    }

    public final ResponseEntity<HangarAuthor> getAuthor(String user) {
        return get("users/" + user, HangarAuthor.class);
    }

    public final ResponseEntity<HangarResource> getResource(String user, String project) {
        return get("projects/" + user + "/" + project, HangarResource.class);
    }

    public final ResponseEntity<byte[]> getResourceIcon(String user, String project) {
        return getImage(IMAGE_BASE_URL + user + "/" + project + "/icon");
    }

    public final ResponseEntity<byte[]> getAuthApiImage(String link) {
        return getImage(link.trim());
    }

    public final ResponseEntity<byte[]> getImage(String url) {
        return get(url, "", byte[].class, headers -> {
            headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));
            return headers;
        });
    }
}
