package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.persistence.SavedBanner;
import com.mcbanners.bannerapi.persistence.SavedBannerRepository;
import com.mcbanners.bannerapi.security.AuthedUserInformation;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController {
    private final SavedBannerRepository repository;

    @Autowired
    public BannerController(SavedBannerRepository repository) {
        this.repository = repository;
    }

    @PostMapping(value = "save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SavedBanner> saveBanner(AuthedUserInformation user, @RequestParam Map<String, String> raw) {
        SavedBanner banner = new SavedBanner();
        banner.setMnemonic(RandomStringUtils.randomAlphabetic(24));

        if (user != null) {
            banner.setOwner(user.getId());
        }

        banner.setSettings(raw);
        banner = repository.save(banner);

        if (repository.existsById(banner.getId())) {
            return new ResponseEntity<>(banner, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to save banner");
        }
    }
}
