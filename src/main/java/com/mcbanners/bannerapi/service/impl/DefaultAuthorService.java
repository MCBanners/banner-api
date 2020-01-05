package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.OreClient;
import com.mcbanners.bannerapi.net.SpigetClient;
import com.mcbanners.bannerapi.obj.backend.ore.OreAuthor;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.obj.backend.spiget.SpigetAuthor;
import com.mcbanners.bannerapi.obj.backend.spiget.SpigetResource;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.api.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@CacheConfig(cacheNames = {"author"})
public class DefaultAuthorService implements AuthorService {
    private final SpigetClient spigetClient;
    private final OreClient oreClient;

    @Autowired
    public DefaultAuthorService(SpigetClient spigetClient, OreClient oreClient) {
        this.spigetClient = spigetClient;
        this.oreClient = oreClient;
    }

    @Override
    @Cacheable
    public Author getAuthor(int authorId, ServiceBackend backend) {
        // At this time, only Spiget supports querying by author ID
        // Fail fast if SPIGET is not the specified ServiceBackend
        if (backend != ServiceBackend.SPIGET) {
            return null;
        }

        SpigetAuthor author = loadSpigetAuthor(authorId);
        SpigetResource[] resources = loadAllSpigetResourcesByAuthor(authorId);

        if (author == null || resources == null) {
            return null;
        }

        int totalDownloads = 0, totalLikes = 0, totalReviews = 0;

        for (SpigetResource resource : resources) {
            totalDownloads += resource.getDownloads();
            totalLikes += resource.getLikes();
            totalReviews += resource.getRating().getCount();
        }

        return new Author(
                author.getName(),
                resources.length,
                author.getIcon().getData(),
                totalDownloads,
                totalLikes,
                totalReviews
        );
    }

    /**
     * Get an author by its name on the specified service bannerapi.
     *
     * @param authorName the author name
     * @param backend    the service bannerapi to query
     * @return the Author object or null if the service bannerapi does not support the operation or the author could not be found.
     */
    @Override
    public final Author getAuthor(String authorName, ServiceBackend backend) {
        // At this time, only Ore supports querying by author name
        // Fail fast if ORE is not the specified ServiceBackend
        if (backend != ServiceBackend.ORE) {
            return null;
        }

        OreAuthor author = loadOreAuthor(authorName);
        if (author == null || author.getProjects() == null) {
            return null;
        }

        int totalDownloads = 0, totalLikes = 0;

        for (OreResource resource : author.getProjects()) {
            totalDownloads += resource.getDownloads();
            totalLikes += resource.getStars();
        }

        String oreAuthorAvatar = loadOreImageByUrl(author.getAvatarUrl());
        if (oreAuthorAvatar == null) {
            oreAuthorAvatar = "";
        }

        return new Author(
                author.getUsername(),
                author.getProjects().length,
                oreAuthorAvatar,
                totalDownloads,
                totalLikes,
                -1 // unknown
        );
    }

    private SpigetAuthor loadSpigetAuthor(int authorId) {
        ResponseEntity<SpigetAuthor> resp = spigetClient.getAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private SpigetResource[] loadAllSpigetResourcesByAuthor(int authorId) {
        ResponseEntity<SpigetResource[]> resp = spigetClient.getAllByAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private OreAuthor loadOreAuthor(String authorId) {
        ResponseEntity<OreAuthor> resp = oreClient.getAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadOreImageByUrl(String url) {
        ResponseEntity<byte[]> resp = oreClient.getAuthApiImage(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }
}
