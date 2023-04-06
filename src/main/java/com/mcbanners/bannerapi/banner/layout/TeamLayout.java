package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.banner.parameter.TeamParameters;
import com.mcbanners.bannerapi.obj.generic.Team;
import com.mcbanners.bannerapi.util.NumberUtil;

import java.util.List;
import java.util.Map;

public class TeamLayout extends Layout<TeamParameters> {
    private final Team team;

    public TeamLayout(Team team, Map<String, String> parameters) {
        super(new TeamParameters(parameters));

        this.team = team;
    }

    @Override
    public List<BasicComponent> build() {
        component(new LogoComponent(
                parameters().getLogo().readX(),
                parameters().getLogo().readSize(),
                team.icon(),
                Sprite.DEFAULT_POLYMART_RES_LOGO
        ));

        text(parameters().getTeamName(), team.name());

        text(parameters().getResourceCount(), "%d resources", team.resourceCount());

        text(parameters().getDownloads(), "%s downloads", NumberUtil.abbreviate(team.resourceDownloads()));

        text(parameters().getRatings(), "%s ratings", NumberUtil.abbreviate(team.resourceRatings()));

        return components();
    }
}
