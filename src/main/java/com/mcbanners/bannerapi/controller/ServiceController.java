package com.mcbanners.backend.controller;

import com.mcbanners.backend.banner.BannerFontFace;
import com.mcbanners.backend.banner.BannerTemplate;
import com.mcbanners.backend.banner.BannerTextAlign;
import com.mcbanners.backend.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("svc")
public class ServiceController {
    @GetMapping(value = "constants", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getTemplates() {
        Map<String, Object> out = new HashMap<>();
        out.put("templates", getEnumValues(BannerTemplate.values()));
        out.put("fonts", getEnumValues(BannerFontFace.values()));
        out.put("text_alignments", getEnumValues(BannerTextAlign.values()));
        return out;
    }

    @GetMapping(value = "template/{template}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getTemplate(@PathVariable("template") String name) {
        BannerTemplate template = BannerTemplate.fromString(name);
        if (template == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found");
        }

        BufferedImage image = template.getImage();
        if (image == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Image could not be generated");
        }

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            ImageIO.write(
                    image,
                    "png",
                    output
            );

            return new ResponseEntity<>(output.toByteArray(), HttpStatus.OK);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    private <T extends Enum<?>> Map<String, String> getEnumValues(T[] values) {
        return Arrays.stream(values).collect(Collectors.toMap(
                k -> k.name().toLowerCase(),
                v -> StringUtil.cleanupEnumConstant(v.name())
        ));
    }
}
