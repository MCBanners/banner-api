package com.mcbanners.bannerapi.service.impl.resource.backend;

import com.mcbanners.bannerapi.net.SpigotClient;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotResource;
import com.mcbanners.bannerapi.obj.generic.PriceInformation;
import com.mcbanners.bannerapi.obj.generic.RatingInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SpigotResourceService {
    private final SpigotClient client;

    @Autowired
    public SpigotResourceService(SpigotClient client) {
        this.client = client;
    }

    public Resource handle(int resourceId) {
        SpigotResource resource = loadResource(resourceId);
        if (resource == null) {
            return null;
        }

        final String rawIcon = resource.iconLink();
        final String[] iconSplit = rawIcon.split("\\?");

        String icon = client.getBase64Image(iconSplit[0]);
        if (icon == null) {
            icon = "";
        }

        boolean isPremium = !resource.price().equals("0.00");

        return new Resource(
                icon,
                resource.title(),
                Integer.parseInt(resource.authorId()),
                resource.authorUsername(),
                new RatingInformation(
                        Integer.parseInt(resource.uniqueReviews()),
                        Double.parseDouble(resource.rating())
                ),
                Integer.parseInt(resource.downloads()),
                isPremium ? new PriceInformation(Double.parseDouble(resource.price()), resource.currency().toUpperCase()) : null,
                null
        );
    }

    private SpigotResource loadResource(int resourceId) {
        final ResponseEntity<SpigotResource> resp = client.getResource(resourceId);
        return resp == null ? null : resp.getBody();
    }
}
