package com.mcbanners.bannerapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcbanners.bannerapi.persistence.SavedBanner;
import com.mcbanners.bannerapi.persistence.SavedBannerRepository;
import com.mcbanners.bannerapi.security.AuthedUserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("manage_saved")
public class ManageController {
    private final SavedBannerRepository repository;
    private final ObjectMapper mapper;

    @Autowired
    public ManageController(SavedBannerRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping(value = "find/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SavedBanner> getAllOwnedBanners(AuthedUserInformation authedUserInformation) {
        return repository.findAllByOwner(authedUserInformation.id());
    }

    @PutMapping(value = "update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SavedBanner updateBanner(AuthedUserInformation authedUserInformation, @PathVariable Long id, @RequestParam Map<String, String> raw) throws JsonProcessingException {
        SavedBanner banner = verifyRequest(authedUserInformation, id);
        banner.setSettings(mapper.writeValueAsString(raw));

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

        if (banner.getOwner() == null || !banner.getOwner().equals(authedUserInformation.id())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't own that banner.");
        }

        return banner;
    }
}
