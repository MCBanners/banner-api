package com.mcbanners.bannerapi.service.impl.author.backend;

import com.mcbanners.bannerapi.net.ModrinthClient;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthResource;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthUser;
import com.mcbanners.bannerapi.obj.generic.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ModrinthAuthorService {
    private final ModrinthClient client;

    @Autowired
    public ModrinthAuthorService(ModrinthClient client) {
        this.client = client;
    }

    public Author handle(String authorName) {
        ModrinthUser author = loadUser(authorName);
        if (author == null) {
            return null;
        }

        ModrinthResource[] projects = loadAllResourcesByAuthor(authorName);
        if (projects == null) {
            return null;
        }

        int totalDownloads = 0, totalFollowers = 0;
        for (ModrinthResource project : projects) {
            totalDownloads += project.downloads();
            totalFollowers += project.followers();
        }

        String modrinthAuthorAvatar = client.getBase64Image(author.avatarUrl());
        if (modrinthAuthorAvatar == null) {
            modrinthAuthorAvatar = "";
        }

        return new Author(
                author.username(),
                projects.length,
                modrinthAuthorAvatar,
                totalDownloads,
                totalFollowers,
                -1
        );
    }

    private ModrinthUser loadUser(String username) {
        final ResponseEntity<ModrinthUser> resp = client.getUserInformation(username);
        return resp == null ? null : resp.getBody();
    }

    private ModrinthResource[] loadAllResourcesByAuthor(String username) {
        final ResponseEntity<ModrinthResource[]> resp = client.getUserProjects(username);
        return resp == null ? null : resp.getBody();
    }
}
