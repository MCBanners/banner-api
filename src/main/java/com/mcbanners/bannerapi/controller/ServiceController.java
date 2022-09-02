package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.banner.param.AuthorParameter;
import com.mcbanners.bannerapi.banner.param.BannerParameter;
import com.mcbanners.bannerapi.banner.param.DiscordParameter;
import com.mcbanners.bannerapi.banner.param.GeneralParameter;
import com.mcbanners.bannerapi.banner.param.MemberParameter;
import com.mcbanners.bannerapi.banner.param.ResourceParameter;
import com.mcbanners.bannerapi.banner.param.ServerParameter;
import com.mcbanners.bannerapi.banner.param.TeamParameter;
import com.mcbanners.bannerapi.util.ParamUtil;
import com.mcbanners.bannerapi.util.StringUtil;
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
import java.util.stream.Stream;

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

    @GetMapping(value = "defaults/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDefaults(@PathVariable String type) {
        Map<String, Class<? extends BannerParameter<Object>>> toSerialize = new HashMap<>();
        type = type.toLowerCase();
        boolean bypass = type.equals("all");

        if (bypass || type.equals("author")) toSerialize.put("author", AuthorParameter.class);
        if (bypass || type.equals("resource")) toSerialize.put("resource", ResourceParameter.class);
        if (bypass || type.equals("member")) toSerialize.put("member", MemberParameter.class);
        if (bypass || type.equals("team")) toSerialize.put("team", TeamParameter.class);
        if (bypass || type.equals("discord")) toSerialize.put("discord", DiscordParameter.class);
        if (bypass || type.equals("server")) toSerialize.put("server", ServerParameter.class);

        Object out;
        if (toSerialize.size() > 1) {
            Map<String, Map<String, Object>> tempOut = new HashMap<>();
            for (Map.Entry<String, Class<? extends BannerParameter<Object>>> entry : toSerialize.entrySet()) {
                tempOut.put(entry.getKey(), mergeWithGeneralParameters(entry.getValue()));
            }
            out = tempOut;
        } else {
            out = mergeWithGeneralParameters(toSerialize.entrySet().iterator().next().getValue());
        }

        return new ResponseEntity<>(out, HttpStatus.OK);
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
                k -> k.name(),
                v -> StringUtil.cleanupEnumConstant(v.name())
        ));
    }

    private Map<String, Object> mergeWithGeneralParameters(Class<? extends BannerParameter<Object>> clazz) {
        return Stream.concat(
                        ParamUtil.makeMap(GeneralParameter.class).entrySet().stream(),
                        ParamUtil.makeMap(clazz).entrySet().stream()
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
