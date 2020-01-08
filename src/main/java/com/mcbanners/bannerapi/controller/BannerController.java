package com.mcbanners.bannerapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcbanners.bannerapi.banner.BannerType;
import com.mcbanners.bannerapi.image.layout.AuthorLayout;
import com.mcbanners.bannerapi.image.layout.Layout;
import com.mcbanners.bannerapi.image.layout.ResourceLayout;
import com.mcbanners.bannerapi.image.layout.ServerLayout;
import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.persistence.SavedBanner;
import com.mcbanners.bannerapi.persistence.SavedBannerRepository;
import com.mcbanners.bannerapi.security.AuthedUserInformation;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.api.AuthorService;
import com.mcbanners.bannerapi.service.api.MinecraftServerService;
import com.mcbanners.bannerapi.service.api.ResourceService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController {
    private final ResourceService resources;
    private final AuthorService authors;
    private final MinecraftServerService servers;
    private final SavedBannerRepository repository;

    @Autowired
    public BannerController(ResourceService resources, SavedBannerRepository repository, AuthorService authors, MinecraftServerService servers) {
        this.resources = resources;
        this.repository = repository;
        this.authors = authors;
        this.servers = servers;
    }

    @PostMapping(value = "save/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SavedBanner> saveBanner(AuthedUserInformation user, @PathVariable BannerType type, @RequestParam Map<String, String> raw) throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(raw));

        SavedBanner banner = new SavedBanner();

        banner.setBannerType(type);

        if (user != null) {
            banner.setOwner(user.getId());
        }

        banner.setMnemonic(RandomStringUtils.randomAlphabetic(24));

        banner.setSettings(raw);
        banner = repository.save(banner);

        if (repository.existsById(banner.getId())) {
            return new ResponseEntity<>(banner, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to save banner");
        }
    }

    @GetMapping(value = "/{mnemonic}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> recall(@PathVariable String mnemonic) {
        SavedBanner banner = repository.findByMnemonic(mnemonic);
        if (banner == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Banner not found");
        }

        Map<String, String> settings = banner.getSettings();

        Layout layout = null;
        switch (banner.getBannerType()) {
            case SPIGOT_AUTHOR:
            case SPONGE_AUTHOR:
                Author author = null;
                switch (banner.getBannerType()) {
                    case SPIGOT_AUTHOR:
                        author = authors.getAuthor(Integer.parseInt(settings.get("_author_id")), ServiceBackend.SPIGET);
                        break;
                    case SPONGE_AUTHOR:
                        author = authors.getAuthor(settings.get("_author_id"), ServiceBackend.ORE);
                        break;
                }

                settings.remove("_author_id");

                if (author == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The stored author could not be found!");
                }

                layout = new AuthorLayout(author, settings);
                break;
            case SPIGOT_RESOURCE:
            case SPONGE_RESOURCE:
                ServiceBackend backend = null;
                Resource resource = null;
                author = null;
                switch (banner.getBannerType()) {
                    case SPIGOT_RESOURCE:
                        backend = ServiceBackend.SPIGET;
                        resource = resources.getResource(Integer.parseInt(settings.get("_resource_id")), backend);
                        author = authors.getAuthor(Integer.parseInt(settings.get("_author_id")), backend);
                        break;
                    case SPONGE_RESOURCE:
                        backend = ServiceBackend.ORE;
                        resource = resources.getResource(settings.get("resource_id"), backend);
                        author = authors.getAuthor(settings.get("author_id"), backend);
                        break;
                }

                settings.remove("_resource_id");
                settings.remove("_author_id");

                if (backend == null) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend not set");
                }

                if (resource == null || author == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Either the stored resource or author could not be found!");
                }

                layout = new ResourceLayout(resource, author, settings, backend);
                break;
            case MINECRAFT_SERVER:
                String host = settings.get("_server_host");
                if (host == null) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Host not specified in banner settings");
                }

                int port = 25565;
                try {
                    port = Integer.parseInt(settings.get("_server_port"));
                } catch (NumberFormatException ignored) {}

                MinecraftServer server = servers.getServer(host, port);
                if (server == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Server not found (or not pingable)!");
                }

                settings.remove("_server_host");
                settings.remove("_server_port");

                layout = new ServerLayout(server, settings);
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedImage rendered = layout.draw();
            ImageIO.write(rendered, "png", bos);
            bos.flush();
            return new ResponseEntity<>(bos.toByteArray(), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(new byte[]{}, HttpStatus.NO_CONTENT);
        }
    }
}
