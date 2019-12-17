package com.mcbanners.backend.controller;

import com.mcbanners.backend.banner.param.res.ResourceParameter;
import com.mcbanners.backend.img.layout.ResourceLayout;
import com.mcbanners.backend.obj.Author;
import com.mcbanners.backend.obj.Resource;
import com.mcbanners.backend.obj.spiget.SpigetAuthor;
import com.mcbanners.backend.obj.spiget.SpigetResource;
import com.mcbanners.backend.spiget.svc.SpigetAuthorService;
import com.mcbanners.backend.spiget.svc.SpigetResourceService;
import com.mcbanners.backend.util.ImageUtil;
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
    private final SpigetResourceService resources;
    private final SpigetAuthorService authors;



    @Autowired
    public ResourceController(SpigetResourceService resources, SpigetAuthorService authors) {
        this.resources = resources;
        this.authors = authors;
    }

    @GetMapping(value = "/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable int id) {
        Resource resource = this.resources.getResource(id);
        return new ResponseEntity<>(Collections.singletonMap("valid", resource != null), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/banner.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable int id, @RequestParam Map<String, String> raw) {
        Resource resource = this.resources.getResource(id);
        if (resource == null) {
            return null;
        }

        Author author = this.authors.getAuthor(resource.getAuthorId());
        if (author == null) {
            return null;
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedImage banner = new ResourceLayout(resource, author, ResourceParameter.parse(raw)).draw();
            ImageIO.write(banner, "png", bos);
            bos.flush();
            return new ResponseEntity<>(bos.toByteArray(), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(new byte[]{}, HttpStatus.NO_CONTENT);
        }
    }
}
