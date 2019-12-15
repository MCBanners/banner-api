package com.mcbanners.backend.svc;

import com.mcbanners.backend.spiget.SpigetClient;
import com.mcbanners.backend.spiget.obj.SpigetAuthor;
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
    public SpigetAuthor getAuthor(int authorId) {
        return loadAuthor(authorId);
    }

    private SpigetAuthor loadAuthor(int authorId) {
        System.out.println("Loading Author from Spiget...");
        return client.getAuthor(authorId).getBody();
    }
}
