package com.mcbanners.bannerapi.service.author.backend;

import com.mcbanners.bannerapi.net.upstream.SpigotClient;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotAuthor;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotResource;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.service.api.BasicHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SpigotAuthorService extends BasicHandler<Author> {
    private final SpigotClient client;

    @Autowired
    public SpigotAuthorService(SpigotClient client) {
        this.client = client;
    }

    @Override
    public Author handle(int authorId) {
        SpigotAuthor author = loadAuthor(authorId);
        SpigotResource[] resources = loadAllResourcesByAuthor(authorId);

        if (author == null || resources == null) {
            return null;
        }

        int totalDownloads = 0, totalReviews = 0;

        for (SpigotResource resource : resources) {
            totalDownloads += Integer.parseInt(resource.downloads());
            totalReviews += Integer.parseInt(resource.totalReviews());
        }

        final String rawIcon = author.avatar();
        final String[] iconSplit = rawIcon.split("\\?");

        String spigotAuthorIcon = client.getBase64Image(iconSplit[0]);

        if (spigotAuthorIcon == null) {
            spigotAuthorIcon = "";
        }

        return new Author(
                author.username(),
                Integer.parseInt(author.resourceCount()),
                spigotAuthorIcon,
                totalDownloads,
                -1,
                totalReviews
        );
    }

    private SpigotAuthor loadAuthor(int authorId) {
        final ResponseEntity<SpigotAuthor> resp = client.getAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }

    private SpigotResource[] loadAllResourcesByAuthor(int authorId) {
        final ResponseEntity<SpigotResource[]> resp = client.getAllByAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }
}
