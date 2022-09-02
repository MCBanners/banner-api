package com.mcbanners.bannerapi.service.impl.author.backend;

import com.mcbanners.bannerapi.net.BuiltByBitClient;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitAuthor;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResource;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResourceBasic;
import com.mcbanners.bannerapi.obj.generic.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BuiltByBitAuthorService {
    private final BuiltByBitClient client;

    @Autowired
    public BuiltByBitAuthorService(BuiltByBitClient client) {
        this.client = client;
    }

    public Author handleBuiltByBit(int authorId) {
        BuiltByBitAuthor author = loadBuiltByBitAuthor(authorId);

        if (author == null) {
            return null;
        }

        BuiltByBitResourceBasic resources = loadBuiltByBitAuthorBasic(authorId);

        if (resources == null || resources.resources().size() == 0) {
            return null;
        }

        int totalDownloads = 0, totalReviews = 0;

        for (final BuiltByBitResource resource : resources.resources()) {
            totalDownloads += resource.downloadCount();
            totalReviews += resource.reviewCount();
        }

        String avatarUrl = client.getBase64Image(author.avatarUrl());

        if (avatarUrl == null) {
            avatarUrl = "";
        }

        return new Author(
                author.username(),
                author.resourceCount(),
                avatarUrl,
                totalDownloads,
                -1,
                totalReviews
        );
    }

    private BuiltByBitResourceBasic loadBuiltByBitAuthorBasic(int authorId) {
        final ResponseEntity<BuiltByBitResourceBasic> resp = client.getAllByAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }

    private BuiltByBitAuthor loadBuiltByBitAuthor(int authorId) {
        final ResponseEntity<BuiltByBitAuthor> resp = client.getAuthor(authorId);
        return resp == null ? null : resp.getBody();
    }
}
