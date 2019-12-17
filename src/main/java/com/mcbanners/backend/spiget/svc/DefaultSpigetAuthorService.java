package com.mcbanners.backend.spiget.svc;

import com.mcbanners.backend.obj.Author;
import com.mcbanners.backend.obj.spiget.SpigetAuthor;
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
        return new Author(author.getName());
    }

    private SpigetAuthor loadAuthor(int authorId) {
        return client.getAuthor(authorId).getBody();
    }
}
