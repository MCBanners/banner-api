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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/spigot/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable int id) {
        Resource resource = this.resources.getResource(id, ServiceBackend.SPIGOT);
        return new ResponseEntity<>(Collections.singletonMap("valid", resource != null), HttpStatus.OK);
    }

    @GetMapping(value = "/sponge/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable String id) {
        Resource resource = this.resources.getResource(id, ServiceBackend.ORE);
        return new ResponseEntity<>(Collections.singletonMap("valid", resource != null), HttpStatus.OK);
    }

    @GetMapping(value = "/spigot/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable int id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Resource resource = this.resources.getResource(id, ServiceBackend.SPIGOT);
        if (resource == null) {
            return null;
        }

        System.out.println(resource.getName());
        System.out.println(resource.getAuthorId());
        System.out.println(resource.getAuthorName());
        System.out.println(resource.getRating().getAverageRating());
        System.out.println(resource.getDownloadCount());

        Author author = this.authors.getAuthor(resource.getAuthorId(), ServiceBackend.SPIGOT);
        if (author == null) {
            return null;
        }

        System.out.println(author.getName());
        System.out.println(author.getResources());

        return draw(resource, author, raw, ServiceBackend.SPIGOT, outputType);
    }

    @GetMapping(value = "/sponge/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable String id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Resource resource = this.resources.getResource(id, ServiceBackend.ORE);
        if (resource == null) {
            return null;
        }

        Author author = this.authors.getAuthor(resource.getAuthorName(), ServiceBackend.ORE);
        if (author == null) {
            return null;
        }

        return draw(resource, author, raw, ServiceBackend.ORE, outputType);
    }

    private ResponseEntity<byte[]> draw(Resource resource, Author author, Map<String, String> raw, ServiceBackend backend, BannerOutputType outputType) {
        return BannerImageWriter.write(new ResourceLayout(resource, author, raw, backend).draw(outputType), outputType);
    }
}
