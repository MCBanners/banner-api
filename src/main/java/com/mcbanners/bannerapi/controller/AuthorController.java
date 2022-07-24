package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.image.BannerImageWriter;
import com.mcbanners.bannerapi.image.layout.AuthorLayout;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.api.AuthorService;
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
@RequestMapping("author")
public class AuthorController {
    private final AuthorService authors;

    @Autowired
    public AuthorController(AuthorService authors) {
        this.authors = authors;
    }

    @GetMapping(value = "/spigot/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable int id) {
        Author author = this.authors.getAuthor(id, ServiceBackend.SPIGOT);
        return new ResponseEntity<>(Collections.singletonMap("valid", author != null), HttpStatus.OK);
    }

    @GetMapping(value = "/sponge/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable String id) {
        Author author = this.authors.getAuthor(id, ServiceBackend.ORE);
        return new ResponseEntity<>(Collections.singletonMap("valid", author != null), HttpStatus.OK);
    }

    @GetMapping(value = "/curseforge/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValidCF(@PathVariable String id) {
        Author author;
        try {
            author = this.authors.getAuthor(Integer.parseInt(id), ServiceBackend.CURSEFORGE);
        } catch (NumberFormatException ex) {
            author = this.authors.getAuthor(id, ServiceBackend.CURSEFORGE);
        }
        return new ResponseEntity<>(Collections.singletonMap("valid", author != null), HttpStatus.OK);
    }

    @GetMapping(value = "/modrinth/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValidModrinth(@PathVariable String id) {
        Author author = this.authors.getAuthor(id, ServiceBackend.MODRINTH);
        return new ResponseEntity<>(Collections.singletonMap("valid", author != null), HttpStatus.OK);
    }

    @GetMapping(value = "/polymart/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValidPolyMart(@PathVariable int id) {
        Author author = this.authors.getAuthor(id, ServiceBackend.POLYMART);
        return new ResponseEntity<>(Collections.singletonMap("valid", author != null), HttpStatus.OK);
    }

    @GetMapping(value = "/mcmarket/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValidMCMarket(@PathVariable int id) {
        Author author = this.authors.getAuthor(id, ServiceBackend.MCMARKET);
        return new ResponseEntity<>(Collections.singletonMap("valid", author != null), HttpStatus.OK);
    }

    @GetMapping(value = "/spigot/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable int id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Author author = this.authors.getAuthor(id, ServiceBackend.SPIGOT);
        if (author == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return draw(author, raw, ServiceBackend.SPIGOT, outputType);
    }

    @GetMapping(value = "/sponge/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable String id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Author author = this.authors.getAuthor(id, ServiceBackend.ORE);
        if (author == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return draw(author, raw, ServiceBackend.ORE, outputType);
    }

    @GetMapping(value = "/curseforge/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBannerCf(@PathVariable String id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Author author;
        try {
            author = this.authors.getAuthor(Integer.parseInt(id), ServiceBackend.CURSEFORGE);
        } catch (NumberFormatException ex) {
            author = this.authors.getAuthor(id, ServiceBackend.CURSEFORGE);
        }

        if (author == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return draw(author, raw, ServiceBackend.CURSEFORGE, outputType);
    }

    @GetMapping(value = "/modrinth/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBannerModrinth(@PathVariable String id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Author author = this.authors.getAuthor(id, ServiceBackend.MODRINTH);
        if (author == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return draw(author, raw, ServiceBackend.MODRINTH, outputType);
    }
    @GetMapping(value = "/polymart/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBannerPolyMart(@PathVariable int id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Author author = this.authors.getAuthor(id, ServiceBackend.POLYMART);
        if (author == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return draw(author, raw, ServiceBackend.POLYMART, outputType);
    }

    @GetMapping(value = "/mcmarket/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBannerMCMarket(@PathVariable int id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Author author = this.authors.getAuthor(id, ServiceBackend.MCMARKET);
        if (author == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return draw(author, raw, ServiceBackend.MCMARKET, outputType);
    }

    private ResponseEntity<byte[]> draw(Author author, Map<String, String> raw, ServiceBackend backend, BannerOutputType outputType) {
        return BannerImageWriter.write(new AuthorLayout(author, raw, backend).draw(outputType), outputType);
    }
}
