package com.mcbanners.bannerapi.service.resource.backend;

import com.mcbanners.bannerapi.net.upstream.OreClient;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.obj.generic.RatingInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.api.BasicHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OreResourceService extends BasicHandler<Resource> {
    private final OreClient client;

    @Autowired
    public OreResourceService(OreClient client) {
        this.client = client;
    }

    @Override
    public Resource handle(String pluginId) {
        OreResource oreResource = loadResource(pluginId);
        if (oreResource == null) {
            return null;
        }

        String oreResourceIcon = client.getBase64Image(oreResource.iconUrl());
        if (oreResourceIcon == null) {
            oreResourceIcon = "";
        }

        return new Resource(
                oreResourceIcon,
                oreResource.name(),
                -1, // not known
                oreResource.owner(), // username
                new RatingInformation(oreResource.stars()),
                oreResource.downloads(),
                null,
                null);
    }

    private OreResource loadResource(String pluginId) {
        final ResponseEntity<OreResource> resp = client.getResource(pluginId);
        return resp == null ? null : resp.getBody();
    }
}
