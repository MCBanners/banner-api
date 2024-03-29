package com.mcbanners.bannerapi.service.author.backend;

import com.mcbanners.bannerapi.net.upstream.PolymartClient;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartAuthor;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartResource;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.service.api.BasicHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PolymartAuthorService extends BasicHandler<Author> {
    private final PolymartClient client;

    @Autowired
    public PolymartAuthorService(PolymartClient client) {
        this.client = client;
    }

    @Override
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
    // TODO: can we get rid of this in any way?
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
