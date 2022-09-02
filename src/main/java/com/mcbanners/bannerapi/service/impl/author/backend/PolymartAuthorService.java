package com.mcbanners.bannerapi.service.impl.author.backend;

import com.mcbanners.bannerapi.net.PolymartClient;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartAuthor;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartResource;
import com.mcbanners.bannerapi.obj.generic.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PolymartAuthorService {
    private final PolymartClient client;

    @Autowired
    public PolymartAuthorService(PolymartClient client) {
        this.client = client;
    }

    public Author handle(final int authorId) {
        final PolymartAuthor author = loadAuthor(authorId);
        if (author == null) {
            return null;
        }

        final String authorImage = client.getBase64Image(author.profilePictureURL());

        return new Author(
                author.username(),
                author.resourceCount(),
                authorImage,
                author.resourceDownloads(),
                -1,
                author.resourceRatings()
        );
    }

    // Major Polymart Workaround
    public Author handle(final int authorId, final int resourceId) {
        final PolymartResource resource = loadResource(resourceId);
        if (resource == null) {
            return null;
        }

        final PolymartAuthor author = resource.ownerType().equals("user")
                ? loadAuthor(authorId) : loadTeam(resource.ownerId());

        if (author == null) {
            return null;
        }

        String ownerImage = client.getBase64Image(author.profilePictureURL());

        return new Author(
                author.username(),
                author.resourceCount(),
                ownerImage,
                author.resourceDownloads(),
                -1,
                author.resourceRatings()
        );
    }

    private PolymartResource loadResource(final int resourceId) {
        final ResponseEntity<PolymartResource> resp = client.getResource(resourceId);
        return resp == null ? null : resp.getBody();
    }

    private PolymartAuthor loadAuthor(final int authorId) {
        final ResponseEntity<PolymartAuthor> resp = client.getAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }

    private PolymartAuthor loadTeam(final int teamId) {
        final ResponseEntity<PolymartAuthor> resp = client.getTeam(teamId);
        return resp == null ? null : resp.getBody();
    }
}
