package com.mcbanners.bannerapi.service.api;

import com.mcbanners.bannerapi.obj.generic.Team;
import com.mcbanners.bannerapi.service.ServiceBackend;

public interface TeamService {

    /**
     * Gets a team by their ID on the providing API service
     *
     * @param teamId the team ID
     * @param backend the service backend to query
     * @return the generic Team object representing the team
     */
    Team getTeam(final int teamId, final ServiceBackend backend);
}
