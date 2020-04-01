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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/spigot/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable int id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Author author = this.authors.getAuthor(id, ServiceBackend.SPIGOT);
        if (author == null) {
            return null;
        }

        return draw(author, raw, outputType);
    }

    @GetMapping(value = "/sponge/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable String id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Author author = this.authors.getAuthor(id, ServiceBackend.ORE);
        if (author == null) {
            return null;
        }

        return draw(author, raw, outputType);
    }

    private ResponseEntity<byte[]> draw(Author author, Map<String, String> raw, BannerOutputType outputType) {
        return BannerImageWriter.write(new AuthorLayout(author, raw).draw(), outputType);
    }
}
