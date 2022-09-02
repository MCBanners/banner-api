package com.mcbanners.bannerapi.service;

import com.mcbanners.bannerapi.net.PolymartClient;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartAuthor;
import com.mcbanners.bannerapi.obj.generic.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"team"})
public class TeamService {
    private final PolymartClient polymartClient;

    @Autowired
    public TeamService(PolymartClient polymartClient) {
        this.polymartClient = polymartClient;
    }

    @Cacheable(unless = "#result == null")
    public Team getTeam(int teamId, ServiceBackend backend) {
        return backend == ServiceBackend.POLYMART ? handlePolymart(teamId) : null;
    }

    private Team handlePolymart(int teamId) {
        final PolymartAuthor team = fetchTeam(teamId);
        return team == null ? null : new Team(
                team.username(),
                polymartClient.getBase64Image(team.profilePictureURL()),
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
}
