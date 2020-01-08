package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.persistence.SavedBanner;
import com.mcbanners.bannerapi.persistence.SavedBannerRepository;
import com.mcbanners.bannerapi.security.AuthedUserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("manage_saved")
public class ManageController {
    private final SavedBannerRepository repository;

    @Autowired
    public ManageController(SavedBannerRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "find/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SavedBanner> getAllOwnedBanners(AuthedUserInformation authedUserInformation) {
        return repository.findAllByOwner(authedUserInformation.getId());
    }
}
