package com.mcbanners.backend.service.impl;

import com.mcbanners.backend.net.SpigetClient;
import com.mcbanners.backend.obj.backend.spiget.SpigetAuthor;
import com.mcbanners.backend.obj.backend.spiget.SpigetResource;
import com.mcbanners.backend.obj.generic.Author;
import com.mcbanners.backend.service.ServiceBackend;
import com.mcbanners.backend.service.api.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"author"})
public class DefaultAuthorService implements AuthorService {
    private final SpigetClient spigetClient;

    @Autowired
    public DefaultAuthorService(SpigetClient client) {
        this.spigetClient = client;
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
     * Get an author by its name on the specified service backend.
     * <strong>This method is not yet implemented.</strong>
     *
     * @param authorName the author name
     * @param backend    the service backend to query
     * @return the Author object or null if the service backend does not support the operation or the author could not be found.
     */
    @Override
    @Deprecated
    public final Author getAuthor(String authorName, ServiceBackend backend) {
        // At this time, only Ore supports querying by author name
        // Fail fast if ORE is not the specified ServiceBackend
        if (backend != ServiceBackend.ORE) {
            return null;
        }

        throw new RuntimeException("not yet implemented");
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
}
