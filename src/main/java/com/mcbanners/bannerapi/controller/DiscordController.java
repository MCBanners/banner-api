package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.BannerImageWriter;
import com.mcbanners.bannerapi.banner.layout.DiscordLayout;
import com.mcbanners.bannerapi.obj.backend.discord.DiscordUser;
import com.mcbanners.bannerapi.service.DiscordService;
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
@RequestMapping("discord")
public class DiscordController {
    private final DiscordService discord;

    @Autowired
    public DiscordController(DiscordService discord) {
        this.discord = discord;
    }

    @GetMapping(value = "/user/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getValid(@PathVariable String id) {
        final DiscordUser user = this.discord.getUser(id);
        return new ResponseEntity<>(Collections.singletonMap("valid", user != null), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable String id, @PathVariable BannerOutputFormat outputType, @RequestParam Map<String, String> raw) {
        final DiscordUser user = this.discord.getUser(id);
        if (user == null) {
            return null;
        }

        return BannerImageWriter.write(new DiscordLayout(user, raw), outputType);
    }
}
