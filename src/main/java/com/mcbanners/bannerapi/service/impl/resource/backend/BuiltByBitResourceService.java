package com.mcbanners.bannerapi.service.impl.resource.backend;

import com.mcbanners.bannerapi.net.BuiltByBitClient;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitAuthor;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResource;
import com.mcbanners.bannerapi.obj.generic.PriceInformation;
import com.mcbanners.bannerapi.obj.generic.RatingInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BuiltByBitResourceService {
    private final BuiltByBitClient client;

    @Autowired
    public BuiltByBitResourceService(BuiltByBitClient client) {
        this.client = client;
    }

    public Resource handleBuiltByBit(int resourceId) {
        BuiltByBitResource resource = loadBuiltByBitResource(resourceId);

        if (resource == null) {
            return null;
        }

        BuiltByBitAuthor author = loadBuiltByBitAuthor(resource.authorId());

        if (author == null) {
            return null;
        }

        boolean isPremium = resource.price() != 0.0;

        int downloadsToShow;

        if (isPremium) {
            downloadsToShow = resource.purchaseCount();
        } else {
            downloadsToShow = resource.downloadCount();
        }

        return new Resource(
                "",
                resource.title(),
                author.authorId(),
                author.username(),
                new RatingInformation(
                        resource.reviewCount(),
                        resource.reviewAverage()
                ),
                downloadsToShow,
                isPremium ? new PriceInformation(resource.price(), resource.currency().toUpperCase()) : null,
                null
        );
    }

    private BuiltByBitResource loadBuiltByBitResource(int resourceId) {
        final ResponseEntity<BuiltByBitResource> resp = client.getResource(resourceId);
        return resp == null ? null : resp.getBody();
    }

    private BuiltByBitAuthor loadBuiltByBitAuthor(int authorId) {
        final ResponseEntity<BuiltByBitAuthor> resp = client.getAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }
}
