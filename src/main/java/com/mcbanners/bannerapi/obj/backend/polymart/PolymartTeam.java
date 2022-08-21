package com.mcbanners.bannerapi.obj.backend.polymart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PolymartTeam {
    private PolymartTeamResponse response;

    public PolymartTeamResponse getResponse() {
        return response;
    }

    public void setResponse(PolymartTeamResponse response) {
        this.response = response;
    }
}
