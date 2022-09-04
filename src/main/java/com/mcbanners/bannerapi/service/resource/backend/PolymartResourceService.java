package com.mcbanners.bannerapi.service.resource.backend;

import com.mcbanners.bannerapi.net.upstream.PolymartClient;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartResource;
import com.mcbanners.bannerapi.obj.generic.PriceInformation;
import com.mcbanners.bannerapi.obj.generic.RatingInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.api.BasicHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PolymartResourceService extends BasicHandler<Resource> {
    private final PolymartClient client;

    @Autowired
    public PolymartResourceService(PolymartClient client) {
        this.client = client;
    }

    public Resource handle(final int resourceId) {
        final PolymartResource resource = loadResource(resourceId);
        if (resource == null) {
            return null;
        }

        final String image = client.getBase64Image(resource.thumbnailURL());
        final boolean isPremium = !(resource.price() == 0.00);

        return new Resource(
                image,
                resource.title(),
                resource.ownerId(),
                resource.ownerName(),
                new RatingInformation(
                        resource.reviewCount(),
                        (double) resource.stars()
                ),
                resource.downloads(),
                isPremium ? new PriceInformation(resource.price(), resource.currency().toUpperCase(Locale.ROOT)) : null,
                null
        );
    }

    private PolymartResource loadResource(final int resourceId) {
        final ResponseEntity<PolymartResource> resp = client.getResource(resourceId);
        return resp == null ? null : resp.getBody();
    }
}
