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

    @GetMapping(value = "/curseforge/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValidCf(@PathVariable int id) {
        Resource resource = this.resources.getResource(id, ServiceBackend.CURSEFORGE);
        return new ResponseEntity<>(Collections.singletonMap("valid", resource != null), HttpStatus.OK);
    }

    @GetMapping(value = "/modrinth/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValidModrinth(@PathVariable String id) {
        Resource resource = this.resources.getResource(id, ServiceBackend.MODRINTH);
        return new ResponseEntity<>(Collections.singletonMap("valid", resource != null), HttpStatus.OK);
    }

    @GetMapping(value = "/builtbybit/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValidBuiltByBit(@PathVariable int id) {
        Resource resource = this.resources.getResource(id, ServiceBackend.BUILTBYBIT);
        return new ResponseEntity<>(Collections.singletonMap("valid", resource != null), HttpStatus.OK);
    }

    @GetMapping(value = "/polymart/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValidPolymart(@PathVariable int id) {
        Resource resource = this.resources.getResource(id, ServiceBackend.POLYMART);
        return new ResponseEntity<>(Collections.singletonMap("valid", resource != null), HttpStatus.OK);
    }

    @GetMapping(value = "/spigot/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable int id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Resource resource = this.resources.getResource(id, ServiceBackend.SPIGOT);
        if (resource == null) {
            return null;
        }

        Author author = this.authors.getAuthor(resource.getAuthorId(), ServiceBackend.SPIGOT);
        if (author == null) {
            return null;
        }

        return draw(resource, author, raw, ServiceBackend.SPIGOT, outputType);
    }

    @GetMapping(value = "/curseforge/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBannerCf(@PathVariable int id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Resource resource = this.resources.getResource(id, ServiceBackend.CURSEFORGE);
        if (resource == null) {
            return null;
        }

        Author author = this.authors.getAuthor(resource.getAuthorId(), ServiceBackend.CURSEFORGE);
        if (author == null) {
            return null;
        }

        return draw(resource, author, raw, ServiceBackend.CURSEFORGE, outputType);
    }

    @GetMapping(value = "/modrinth/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBannerModrinth(@PathVariable String id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Resource resource = this.resources.getResource(id, ServiceBackend.MODRINTH);
        if (resource == null) {
            return null;
        }

        Author author = this.authors.getAuthor(resource.getAuthorName(), ServiceBackend.MODRINTH);
        if (author == null) {
            return null;
        }

        return draw(resource, author, raw, ServiceBackend.MODRINTH, outputType);
    }

    @GetMapping(value = "/builtbybit/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBannerBuiltByBit(@PathVariable int id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Resource resource = this.resources.getResource(id, ServiceBackend.BUILTBYBIT);
        if (resource == null) {
            return null;
        }

        Author author = this.authors.getAuthor(resource.getAuthorId(), ServiceBackend.BUILTBYBIT);
        if (author == null) {
            return null;
        }

        return draw(resource, author, raw, ServiceBackend.BUILTBYBIT, outputType);
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

    @GetMapping(value = "/polymart/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBannerPolymart(@PathVariable int id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Resource resource = this.resources.getResource(id, ServiceBackend.POLYMART);
        if (resource == null) {
            return null;
        }

        Author author = this.authors.getAuthor(resource.getAuthorId(), id, ServiceBackend.POLYMART);
        if (author == null) {
            return null;
        }

        return draw(resource, author, raw, ServiceBackend.POLYMART, outputType);
    }

    private ResponseEntity<byte[]> draw(Resource resource, Author author, Map<String, String> raw, ServiceBackend backend, BannerOutputType outputType) {
        return BannerImageWriter.write(new ResourceLayout(resource, author, raw, backend).draw(outputType), outputType);
    }
}
