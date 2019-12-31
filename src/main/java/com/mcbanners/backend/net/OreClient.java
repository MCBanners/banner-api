package com.mcbanners.backend.net;

import com.mcbanners.backend.obj.backend.ore.OreAuthor;
import com.mcbanners.backend.obj.backend.ore.OreResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OreClient extends BasicHttpClient {
    public OreClient() {
        super("https://ore.spongepowered.org/api/v1/");
    }

    public final ResponseEntity<OreAuthor> getAuthor(String authorId) {
        return get("users/" + authorId, OreAuthor.class);
    }

    public final ResponseEntity<OreResource> getResource(String pluginId) {
        return get("projects/" + pluginId, OreResource.class);
    }
}
