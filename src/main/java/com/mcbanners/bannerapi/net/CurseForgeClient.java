package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.net.error.FurtherProcessingRequiredException;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeAuthor;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;
import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.Collections;

@Component
public class CurseForgeClient extends BasicHttpClient {
    public CurseForgeClient() {
        super("https://api.cfwidget.com/");
    }

    public final ResponseEntity<CurseForgeResource> getResource(int resourceId) throws FurtherProcessingRequiredException {
        try {
            return get(String.valueOf(resourceId), CurseForgeResource.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Curse Resource by id %d: %s", resourceId, ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }

    public final ResponseEntity<CurseForgeAuthor> getAuthor(int authorId) {
        try {
            return get("author/" + authorId, CurseForgeAuthor.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Curse Author by id %d: %s", authorId, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public final ResponseEntity<CurseForgeAuthor> getAuthor(String authorName) {
        try {
            return get("author/search/" + authorName, CurseForgeAuthor.class);
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load Curse Author by username %s: %s", authorName, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
