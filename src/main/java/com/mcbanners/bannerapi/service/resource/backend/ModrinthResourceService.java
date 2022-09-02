package com.mcbanners.bannerapi.service.resource.backend;

import com.mcbanners.bannerapi.net.ModrinthClient;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthResource;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthUser;
import com.mcbanners.bannerapi.obj.generic.RatingInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.api.BasicHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ModrinthResourceService extends BasicHandler<Resource> {
    private final ModrinthClient client;

    @Autowired
    public ModrinthResourceService(ModrinthClient client) {
        this.client = client;
    }

    @Override
    public Resource handle(String pluginId) {
        ModrinthResource modrinthResource = loadResource(pluginId);
        if (modrinthResource == null) {
            return null;
        }

        String modrinthResourceIcon = client.getBase64Image(modrinthResource.iconUrl());
        if (modrinthResourceIcon == null) {
            modrinthResourceIcon = "";
        }

        ModrinthUser mainAuthor = loadMainProjectAuthor(pluginId);
        if (mainAuthor == null) {
            return null;
        }

        return new Resource(
                modrinthResourceIcon,
                modrinthResource.title(),
                -1,
                mainAuthor.username(),
                new RatingInformation(0, 0.0),
                modrinthResource.downloads(),
                null,
                modrinthResource.updated());
    }

    private ModrinthResource loadResource(String pluginId) {
        final ResponseEntity<ModrinthResource> resp = client.getResource(pluginId);
        return resp == null ? null : resp.getBody();
    }

    private ModrinthUser loadMainProjectAuthor(String pluginId) {
        final ResponseEntity<ModrinthUser> resp = client.getMainProjectAuthor(pluginId);
        return resp == null ? null : resp.getBody();
    }
}
