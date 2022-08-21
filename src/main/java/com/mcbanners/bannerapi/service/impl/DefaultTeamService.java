package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.PolymartClient;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartTeam;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartTeamData;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartTeamStatistics;
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
        final PolymartTeam team = loadPolymartTeam(teamId);

        if (team == null) {
            return null;
        }

        final PolymartTeamData data = team.getResponse().getTeam();
        final PolymartTeamStatistics statistics = data.getStatistics();

        return new Team(
                data.getName(),
                data.getProfilePictureURL(),
                statistics.getResourceCount(),
                statistics.getResourceDownloads(),
                statistics.getResourceRatings(),
                statistics.getResourceAverageRating()
        );
    }

    private PolymartTeam loadPolymartTeam(int teamId) {
        ResponseEntity<PolymartTeam> resp = polymartClient.getTeam(teamId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadPolymartTeamIcon(String url) {
        ResponseEntity<byte[]> resp = polymartClient.getIcon(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }
}
