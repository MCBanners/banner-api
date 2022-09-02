package com.mcbanners.bannerapi.service.impl.author.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcbanners.bannerapi.net.OreClient;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;
import com.mcbanners.bannerapi.obj.generic.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OreAuthorService {
    private final OreClient client;
    private final ObjectMapper mapper;

    @Autowired
    public OreAuthorService(OreClient client) {
        this.client = client;
        this.mapper = new ObjectMapper();
    }

    public Author handle(String authorName) {
        OreResource[] resources = loadAllResourcesByAuthor(authorName);
        if (resources == null) {
            return null;
        }

        // TODO: what if they have more than 25 projects
        int totalDownloads = 0, totalLikes = 0;
        for (OreResource resource : resources) {
            totalDownloads += resource.downloads();
            totalLikes += resource.stars();
        }

        // Todo figure out why this is weird
        String oreAuthorAvatar = client.getBase64Image(authorName);
        if (oreAuthorAvatar == null) {
            oreAuthorAvatar = "";
        }

        // TODO: what if they have no projects (resources[0]?)
        return new Author(
                resources[0].owner(),
                resources.length,
                oreAuthorAvatar,
                totalDownloads,
                totalLikes,
                -1 // unknown
        );
    }

    private OreResource[] loadAllResourcesByAuthor(String authorId) {
        ResponseEntity<JsonNode> resp = client.getProjectsFromAuthor(authorId);
        if (resp == null) {
            return null;
        }

        JsonNode data = resp.getBody();
        if (data == null || (!data.has("result") || !data.get("result").isArray())) {
            return null;
        }

        return mapper.convertValue(data.get("result"), OreResource[].class);
    }
}
