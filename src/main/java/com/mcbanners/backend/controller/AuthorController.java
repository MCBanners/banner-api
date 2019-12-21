package com.mcbanners.backend.controller;

import com.mcbanners.backend.banner.param.author.AuthorParamter;
import com.mcbanners.backend.img.layout.AuthorLayout;
import com.mcbanners.backend.obj.Author;
import com.mcbanners.backend.spiget.svc.SpigetAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("author")
public class AuthorController {
    private final SpigetAuthorService authors;

    @Autowired
    public AuthorController(SpigetAuthorService authors) {
        this.authors = authors;
    }

    @GetMapping(value = "/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable int id) {
        Author author = this.authors.getAuthor(id);
        return new ResponseEntity<>(Collections.singletonMap("valid", author != null), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/banner.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable int id, @RequestParam Map<String, String> raw) {
        Author author = this.authors.getAuthor(id);
        if (author == null) {
            return null;
        }

        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedImage banner = new AuthorLayout(author, AuthorParamter.parse(raw)).draw();
            ImageIO.write(banner, "png", bos);
            bos.flush();
            return new ResponseEntity<>(bos.toByteArray(), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(new byte[]{}, HttpStatus.NO_CONTENT);
        }
    }
}
