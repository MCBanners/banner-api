package com.mcbanners.backend.controller;

import com.mcbanners.backend.spiget.SpigetClient;
import com.mcbanners.backend.spiget.obj.SpigetAuthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("author")
public class AuthorController {
    private final SpigetClient client;

    @Autowired
    public AuthorController(SpigetClient client) {
        this.client = client;
    }

    @GetMapping(value = "/{id}/banner.png", produces = "application/json")
    public SpigetAuthor getBanner(@PathVariable int id) {
        return client.getAuthor(id).getBody();
    }
}
