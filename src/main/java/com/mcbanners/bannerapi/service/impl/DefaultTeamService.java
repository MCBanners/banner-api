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
        if (backend == ServiceBackend.POLYMART) {
            return handlePolymart(teamId);
        }
        return null;
    }

    private Team handlePolymart(int teamId) {
        final PolymartAuthor team = loadPolymartTeam(teamId);
        if (team == null) {
            return null;
        }

        final String image = loadPolymartTeamIcon(team.profilePictureURL());

        return new Team(
                team.username(),
                image,
                team.resourceCount(),
                team.resourceDownloads(),
                team.resourceRatings(),
                team.resourceAverageRating()
        );
    }

    private PolymartAuthor loadPolymartTeam(int teamId) {
        ResponseEntity<PolymartAuthor> resp = polymartClient.getTeam(teamId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadPolymartTeamIcon(String url) {
        ResponseEntity<byte[]> resp = polymartClient.getImage(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }
}
