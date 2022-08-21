package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolymartTeamResponse {
    private boolean success;
    private PolymartTeamData team;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PolymartTeamData getTeam() {
        return team;
    }

    public void setTeam(PolymartTeamData team) {
        this.team = team;
    }
}
