package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.persistence.SavedBanner;
import com.mcbanners.bannerapi.persistence.SavedBannerRepository;
import com.mcbanners.bannerapi.security.AuthedUserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

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

    @PutMapping(value = "update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SavedBanner updateBanner(AuthedUserInformation authedUserInformation, @PathVariable Long id, @RequestParam Map<String, String> raw) {
        SavedBanner banner = verifyRequest(authedUserInformation, id);

        banner.setSettings(raw);

        return repository.save(banner);
    }

    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteBanner(AuthedUserInformation authedUserInformation, @PathVariable Long id) {
        verifyRequest(authedUserInformation, id);
        repository.deleteById(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    private SavedBanner verifyRequest(AuthedUserInformation authedUserInformation, @PathVariable Long id) {
        SavedBanner banner = repository.findById(id).orElse(null);

        if (banner == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Banner not found.");
        }

        if (!banner.getOwner().equals(authedUserInformation.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't own that banner.");
        }

        return banner;
    }
}
