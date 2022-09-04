package com.mcbanners.bannerapi.service.author.backend;

import com.mcbanners.bannerapi.net.upstream.CurseForgeClient;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeAuthor;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.service.api.BasicHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurseForgeAuthorService extends BasicHandler<Author> {
    private final CurseForgeClient client;

    @Autowired
    public CurseForgeAuthorService(CurseForgeClient client) {
        this.client = client;
    }

    @Override
    public Author handle(String id) {
        try {
            return handle(Integer.parseInt(id));
        } catch (NumberFormatException ignored) {
            return handle(loadAuthor(id));
        }
    }

    @Override
    public Author handle(int id) {
        return handle(loadAuthor(id));
    }

    private Author handle(final CurseForgeAuthor author) {
        if (author == null) {
            return null;
        }

        List<CurseForgeResource> resources = loadAllResourcesByAuthor(author);

        int totalDownloads = 0;
        for (CurseForgeResource resource : resources) {
            totalDownloads += resource.totalDownloads();
        }

        return new Author(
                author.username(),
                author.projects().size(),
                "",
                totalDownloads,
                -1,
                -1
        );
    }

    private CurseForgeAuthor loadAuthor(int authorId) {
        final ResponseEntity<CurseForgeAuthor> resp = client.getAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }

    private CurseForgeAuthor loadAuthor(String authorName) {
        final ResponseEntity<CurseForgeAuthor> resp = client.getAuthor(authorName);
        return resp == null ? null : resp.getBody();
    }

    private List<CurseForgeResource> loadAllResourcesByAuthor(CurseForgeAuthor author) {
        List<CurseForgeResource> resources = new ArrayList<>();

        for (CurseForgeAuthor.Project project : author.projects()) {
            ResponseEntity<CurseForgeResource> resp = client.getResource(project.id());
            if (resp != null) {
                resources.add(resp.getBody());
            }
        }
        return resources;
    }
}
