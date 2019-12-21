package com.mcbanners.backend.spiget.svc;

import com.mcbanners.backend.obj.Author;
import com.mcbanners.backend.obj.spiget.SpigetAuthor;
import com.mcbanners.backend.obj.spiget.SpigetResource;
import com.mcbanners.backend.spiget.SpigetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"author"})
public class DefaultSpigetAuthorService implements SpigetAuthorService {
    private final SpigetClient client;

    @Autowired
    public DefaultSpigetAuthorService(SpigetClient client) {
        this.client = client;
    }

    @Cacheable
    @Override
    public Author getAuthor(int authorId) {
        SpigetAuthor author = loadAuthor(authorId);
        SpigetResource[] resources = loadAllByAuthor(authorId);
        int downloads = 0;
        int likes = 0;
        int reviews = 0;
        for(SpigetResource r : resources) {
            downloads += r.getDownloads();
            likes += r.getLikes();
            reviews += r.getRating().getCount();
        }
        return new Author(author.getName(), resources.length, author.getIcon().getData(), downloads, likes, reviews);
    }

    private SpigetAuthor loadAuthor(int authorId) {
        return client.getAuthor(authorId).getBody();
    }

    private SpigetResource[] loadAllByAuthor(int authorId) {
        return client.getAllByAuthor(authorId).getBody();
    }
}
