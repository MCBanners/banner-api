package com.mcbanners.bannerapi.service.resource.backend;

import com.mcbanners.bannerapi.net.upstream.BuiltByBitClient;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitAuthor;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResource;
import com.mcbanners.bannerapi.obj.generic.PriceInformation;
import com.mcbanners.bannerapi.obj.generic.RatingInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.api.BasicHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BuiltByBitResourceService extends BasicHandler<Resource> {
    private final BuiltByBitClient client;

    @Autowired
    public BuiltByBitResourceService(BuiltByBitClient client) {
        this.client = client;
    }

    @Override
    public Resource handle(int resourceId) {
        BuiltByBitResource resource = loadResource(resourceId);

        if (resource == null) {
            return null;
        }

        BuiltByBitAuthor author = loadAuthor(resource.authorId());

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

    private BuiltByBitResource loadResource(int resourceId) {
        final ResponseEntity<BuiltByBitResource> resp = client.getResource(resourceId);
        return resp == null ? null : resp.getBody();
    }

    private BuiltByBitAuthor loadAuthor(int authorId) {
        final ResponseEntity<BuiltByBitAuthor> resp = client.getAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }
}
