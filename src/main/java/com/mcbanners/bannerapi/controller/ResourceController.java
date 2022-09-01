package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.image.BannerImageWriter;
import com.mcbanners.bannerapi.image.layout.ResourceLayout;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.api.AuthorService;
import com.mcbanners.bannerapi.service.api.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("resource")
public class ResourceController {
    private final ResourceService resources;
    private final AuthorService authors;

    @Autowired
    public ResourceController(ResourceService resources, AuthorService authors) {
        this.resources = resources;
        this.authors = authors;
    }

    @GetMapping(value = "/{platform}/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> checkValid(@PathVariable ServiceBackend platform, @PathVariable String id) {
        final Resource resource = switch (platform) {
            case SPIGOT, POLYMART, BUILTBYBIT, CURSEFORGE -> this.resources.getResource(Integer.parseInt(id), platform);
            case ORE, MODRINTH -> this.resources.getResource(id, platform);
        };
        return new ResponseEntity<>(Collections.singletonMap("valid", resource != null), HttpStatus.OK);
    }

    @GetMapping(value = "/{platform}/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable ServiceBackend platform, @PathVariable String id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        final Resource resource = switch (platform) {
            case SPIGOT, POLYMART, BUILTBYBIT, CURSEFORGE -> this.resources.getResource(Integer.parseInt(id), platform);
            case ORE, MODRINTH -> this.resources.getResource(id, platform);
        };

        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final Author author = switch (platform) {
            case SPIGOT, POLYMART, BUILTBYBIT, CURSEFORGE -> this.authors.getAuthor(resource.authorId(), platform);
            case ORE, MODRINTH -> this.authors.getAuthor(resource.authorName(), platform);
        };

        if (author == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return draw(resource, author, raw, platform, outputType);
    }

    private ResponseEntity<byte[]> draw(Resource resource, Author author, Map<String, String> raw, ServiceBackend backend, BannerOutputType outputType) {
        return BannerImageWriter.write(new ResourceLayout(resource, author, raw, backend).draw(outputType), outputType);
    }
}
