package com.mcbanners.backend.controller;

import com.mcbanners.backend.banner.BannerFontFace;
import com.mcbanners.backend.banner.BannerTemplate;
import com.mcbanners.backend.banner.BannerTextAlign;
import com.mcbanners.backend.util.StringUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        out.put("text-alignments", getEnumValues(BannerTextAlign.values()));
        return out;
    }

    private <T extends Enum<?>> Map<String, String> getEnumValues(T[] values) {
        return Arrays.stream(values).collect(Collectors.toMap(
                k -> k.name().toLowerCase(),
                v -> StringUtil.cleanupEnumConstant(v.name())
        ));
    }
}
