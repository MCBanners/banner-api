package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.banner.BackgroundTemplate;
import com.mcbanners.bannerapi.banner.FontFace;
import com.mcbanners.bannerapi.banner.TextAlign;
import com.mcbanners.bannerapi.banner.parameter.AuthorParameters;
import com.mcbanners.bannerapi.banner.parameter.DiscordParameters;
import com.mcbanners.bannerapi.banner.parameter.MemberParameters;
import com.mcbanners.bannerapi.banner.parameter.ResourceParameters;
import com.mcbanners.bannerapi.banner.parameter.ServerParameters;
import com.mcbanners.bannerapi.banner.parameter.TeamParameters;
import com.mcbanners.bannerapi.util.StringUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = {"service"})
public class ServiceController {
    @Cacheable
    @GetMapping(value = "constants", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getConstants() {
        Map<String, Object> out = new HashMap<>();
        out.put("templates", getEnumValues(BackgroundTemplate.values()));
        out.put("fonts", getEnumValues(FontFace.values()));
        out.put("text_alignments", getEnumValues(TextAlign.values()));
        return out;
    }

    @Cacheable
    @GetMapping(value = "defaults/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDefaults(@PathVariable String type) {
        Map<String, Object> out = new HashMap<>();

        type = type.toLowerCase();
        boolean bypass = type.equals("all");

        if (bypass || type.equals("author")) out.put("author", new AuthorParameters(null).defaults());
        if (bypass || type.equals("resource")) out.put("resource", new ResourceParameters(null).defaults());
        if (bypass || type.equals("member")) out.put("member", new MemberParameters(null).defaults());
        if (bypass || type.equals("team")) out.put("team", new TeamParameters(null).defaults());
        if (bypass || type.equals("discord")) out.put("discord", new DiscordParameters(null).defaults());
        if (bypass || type.equals("server")) out.put("server", new ServerParameters(null).defaults());

        return new ResponseEntity<>(out, HttpStatus.OK);
    }


    @Cacheable
    @GetMapping(value = "template/{template}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getTemplate(@PathVariable("template") String name) {
        BackgroundTemplate template = BackgroundTemplate.fromString(name);
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
                k -> k.name(),
                v -> StringUtil.cleanupEnumConstant(v.name())
        ));
    }
}
