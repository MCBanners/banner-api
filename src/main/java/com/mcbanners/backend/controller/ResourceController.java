package com.mcbanners.backend.controller;

import com.mcbanners.backend.banner.param.resource.ResourceParameter;
import com.mcbanners.backend.image.layout.ResourceLayout;
import com.mcbanners.backend.obj.generic.Author;
import com.mcbanners.backend.obj.generic.Resource;
import com.mcbanners.backend.service.ServiceBackend;
import com.mcbanners.backend.service.api.AuthorService;
import com.mcbanners.backend.service.api.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        Resource resource = this.resources.getResource(id, ServiceBackend.SPIGET);
        return new ResponseEntity<>(Collections.singletonMap("valid", resource != null), HttpStatus.OK);
    }

    @GetMapping(value = "/sponge/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable String id) {
        Resource resource = this.resources.getResource(id, ServiceBackend.ORE);
        return new ResponseEntity<>(Collections.singletonMap("valid", resource != null), HttpStatus.OK);
    }

    @GetMapping(value = "/spigot/{id}/banner.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable int id, @RequestParam Map<String, String> raw) {
        Resource resource = this.resources.getResource(id, ServiceBackend.SPIGET);
        if (resource == null) {
            return null;
        }

        Author author = this.authors.getAuthor(resource.getAuthorId(), ServiceBackend.SPIGET);
        if (author == null) {
            return null;
        }

        return draw(resource, author, raw, ServiceBackend.SPIGET);
    }

    @GetMapping(value = "/sponge/{id}/banner.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable String id, @RequestParam Map<String, String> raw) {
        Resource resource = this.resources.getResource(id, ServiceBackend.ORE);
        if (resource == null) {
            return null;
        }

        Author author = this.authors.getAuthor(resource.getAuthorName(), ServiceBackend.ORE);
        if (author == null) {
            return null;
        }

        return draw(resource, author, raw, ServiceBackend.ORE);
    }

    private ResponseEntity<byte[]> draw(Resource resource, Author author, Map<String, String> raw, ServiceBackend backend) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedImage banner = new ResourceLayout(resource, author, ResourceParameter.parse(raw), backend).draw();
            ImageIO.write(banner, "png", bos);
            bos.flush();
            return new ResponseEntity<>(bos.toByteArray(), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(new byte[]{}, HttpStatus.NO_CONTENT);
        }
    }
}
