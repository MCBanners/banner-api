package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.PolymartClient;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartAuthor;
import com.mcbanners.bannerapi.obj.generic.Team;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.api.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@CacheConfig(cacheNames = {"team"})
public class DefaultTeamService implements TeamService {
    private final PolymartClient polymartClient;

    @Autowired
    public DefaultTeamService(PolymartClient polymartClient) {
        this.polymartClient = polymartClient;
    }

    @Override
    @Cacheable(unless = "#result == null")
    public Team getTeam(int teamId, ServiceBackend backend) {
        return backend == ServiceBackend.POLYMART ? handlePolymart(teamId) : null;
    }

    private Team handlePolymart(int teamId) {
        final PolymartAuthor team = fetchTeam(teamId);
        return team == null ? null : new Team(
                team.username(),
                fetchIcon(team.profilePictureURL()),
                team.resourceCount(),
                team.resourceDownloads(),
                team.resourceRatings(),
                team.resourceAverageRating()
        );
    }

    private PolymartAuthor fetchTeam(int teamId) {
        final ResponseEntity<PolymartAuthor> resp = polymartClient.getTeam(teamId);
        return resp == null ? null : resp.getBody();
    }

    private String fetchIcon(String url) {
        final ResponseEntity<byte[]> resp = polymartClient.getImage(url);
        return resp == null ? null : Base64.getEncoder().encodeToString(resp.getBody());
    }
}
