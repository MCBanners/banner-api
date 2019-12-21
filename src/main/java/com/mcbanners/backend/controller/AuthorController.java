package com.mcbanners.backend.controller;

import com.mcbanners.backend.obj.Author;
import com.mcbanners.backend.spiget.svc.SpigetAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("author")
public class AuthorController {
    private final SpigetAuthorService authors;

    @Autowired
    public AuthorController(SpigetAuthorService authors) {
        this.authors = authors;
    }

    @GetMapping(value = "/{id}/banner.png", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> getBanner(@PathVariable int id) {
        Author author = this.authors.getAuthorResources(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
}
