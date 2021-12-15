package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.net.error.FurtherProcessingRequiredException;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeAuthor;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientResponseException;

import java.io.IOException;
import java.util.Collections;

@Component
public class CurseForgeClient extends BasicHttpClient {
    public CurseForgeClient() {
        super("https://api.cfwidget.com/");
    }

    public final ResponseEntity<CurseForgeResource> getResource(int resourceId) {
        try {
            return get(String.valueOf(resourceId), CurseForgeResource.class);
        } catch (RestClientResponseException ex) {
            if (ex.getRawStatusCode() == 202) {
                throw new FurtherProcessingRequiredException(
                        "CurseForge is currently processing the requested resource and has asked us to wait while " +
                                "the processing completes. Please try your request again in about 30 seconds. Sorry " +
                                "for the inconvenience.");
            }
        }

        return null;
    }

    public final ResponseEntity<CurseForgeAuthor> getAuthor(int authorId) {
        try {
            return get("author/" + authorId, CurseForgeAuthor.class);
        } catch (RestClientResponseException ignored) {
            return null;
        }
    }

    public final ResponseEntity<CurseForgeAuthor> getAuthor(String authorName) {
        try {
            return get("author/search/" + authorName, CurseForgeAuthor.class);
        } catch (RestClientResponseException ignored) {
            return null;
        }
    }

    public final ResponseEntity<byte[]> getResourceIcon(String url) {
        try {
            return get(url, "", byte[].class, headers -> {
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));
                return headers;
            });
        } catch (RestClientResponseException ex) {
            return null;
        }
    }
}
