package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeAuthor;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CurseForgeClient extends BasicHttpClient {
    public CurseForgeClient() {
        super("https://api.dev.cfwidget.com/");
    }

    public final ResponseEntity<CurseForgeResource> getResource(int resourceId) {
        return get(String.valueOf(resourceId), CurseForgeResource.class);
    }

    public final ResponseEntity<CurseForgeAuthor> getAuthor(int authorId) {
        return get("author/" + authorId, CurseForgeAuthor.class);
    }

    public final ResponseEntity<CurseForgeAuthor> getAuthor(String authorName) {
        return get("author/search/" + authorName, CurseForgeAuthor.class);
    }

    public final ResponseEntity<byte[]> getResourceIcon(String url) {
        return get(url, "", byte[].class, headers -> {
            headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));
            return headers;
        });
    }
}
