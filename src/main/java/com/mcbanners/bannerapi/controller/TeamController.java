package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.image.BannerImageWriter;
import com.mcbanners.bannerapi.image.layout.TeamLayout;
import com.mcbanners.bannerapi.obj.generic.Team;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.api.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("team")
public class TeamController {
    private final TeamService teams;

    @Autowired
    public TeamController(TeamService teams) {
        this.teams = teams;
    }

    @GetMapping(value = "/polymart/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable int id) {
        final Team team = this.teams.getTeam(id, ServiceBackend.POLYMART);
        return new ResponseEntity<>(Collections.singletonMap("valid", team != null), HttpStatus.OK);
    }

    @GetMapping(value = "/polymart/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable int id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        final Team team = this.teams.getTeam(id, ServiceBackend.POLYMART);
        if (team == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return BannerImageWriter.write(new TeamLayout(team, raw, ServiceBackend.POLYMART).draw(outputType), outputType);
    }
}
