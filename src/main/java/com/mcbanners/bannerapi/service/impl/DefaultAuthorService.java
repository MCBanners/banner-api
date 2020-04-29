package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.OreClient;
import com.mcbanners.bannerapi.net.SpigotClient;
import com.mcbanners.bannerapi.obj.backend.ore.OreAuthor;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotAuthor;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotResource;
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
    private final SpigotClient spigotClient;
    private final OreClient oreClient;

    @Autowired
    public DefaultAuthorService(SpigotClient spigotClient, OreClient oreClient) {
        this.spigotClient = spigotClient;
        this.oreClient = oreClient;
    }

    @Override
    @Cacheable
    public Author getAuthor(int authorId, ServiceBackend backend) {
        // At this time, only Spigot supports querying by author ID
        // Fail fast if SPIGOT is not the specified ServiceBackend
        if (backend != ServiceBackend.SPIGOT) {
            return null;
        }

        SpigotAuthor author = loadSpigotAuthor(authorId);
        SpigotResource[] resources = loadAllSpigotResourcesByAuthor(authorId);

        if (author == null || resources == null) {
            return null;
        }

        int totalDownloads = 0, totalLikes = 0, totalReviews = 0;

        for (SpigotResource resource : resources) {
            totalDownloads += Integer.parseInt(resource.getStats().getDownloads());
            totalReviews += Integer.parseInt(resource.getStats().getReviews());
        }

        String hash = author.getAvatar().getHash();
        String info = author.getAvatar().getInfo();

        String authorAvatarUrl = "";

        if (hash != null && !hash.isEmpty()) {
            authorAvatarUrl = String.format("http://gravatar.com/avatar/%s.jpg?s=96", author.getAvatar().getHash());
        }
        else if (info != null && !info.isEmpty()) {
            int imageFolder = authorId / 1000;
            authorAvatarUrl = String.format("https://www.spigotmc.org/data/avatars/l/%d/%d.jpg?%s", imageFolder, authorId, info);
        }

        String spigotAuthorIcon = loadSpigotAuthorIcon(authorAvatarUrl);
        if (spigotAuthorIcon == null) {
            spigotAuthorIcon = "";
        }


        return new Author(
                author.getUsername(),
                Integer.parseInt(author.getResource_count()),
                spigotAuthorIcon,
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
    @Cacheable
    public Author getAuthor(String authorName, ServiceBackend backend) {
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

    private SpigotAuthor loadSpigotAuthor(int authorId) {
        ResponseEntity<SpigotAuthor> resp = spigotClient.getAuthor(authorId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private SpigotResource[] loadAllSpigotResourcesByAuthor(int authorId) {
        ResponseEntity<SpigotResource[]> resp = spigotClient.getAllByAuthor(authorId);
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

    private String loadSpigotAuthorIcon(String url) {
        ResponseEntity<byte[]> resp = spigotClient.getResourceIcon(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }
}
