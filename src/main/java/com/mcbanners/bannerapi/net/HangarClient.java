package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.hangar.HangarAuthor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class HangarClient extends BasicHttpClient {

    public HangarClient() {
        super("https://hangar.benndorf.dev/api/v1/");
    }

    public final ResponseEntity<HangarAuthor> getAuthor(String user) {
        return get("users/" + user, HangarAuthor.class);
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
