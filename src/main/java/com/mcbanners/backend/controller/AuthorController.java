package com.mcbanners.backend.controller;

import com.mcbanners.backend.obj.SpigetAuthor;
import com.mcbanners.backend.spiget.svc.SpigetAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/{id}/banner.png", produces = "application/json")
    public SpigetAuthor getBanner(@PathVariable int id) {
        return authors.getAuthor(id);
    }
}
