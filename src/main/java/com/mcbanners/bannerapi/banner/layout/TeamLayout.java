package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.ImageBuilder;
import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.banner.parameter.TeamParameters;
import com.mcbanners.bannerapi.obj.generic.Team;
import com.mcbanners.bannerapi.util.NumberUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class TeamLayout extends Layout {
    private final Team team;
    private final TeamParameters teamParameters;

    public TeamLayout(Team team, Map<String, String> parameters) {
        this.team = team;
        this.teamParameters = new TeamParameters(parameters);
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(teamParameters.getBackground().readTemplate());

        addComponent(new LogoComponent(
                teamParameters.getLogo().readX(),
                teamParameters.getLogo().readSize(),
                team.icon(),
                Sprite.DEFAULT_POLYMART_RES_LOGO
        ));

        addComponent(teamParameters.getTeamName().asTextComponent(textColor, team.name()));
        addComponent(teamParameters.getResourceCount().asTextComponent(textColor, team.resourceCount() + " resources"));
        addComponent(teamParameters.getDownloads().asTextComponent(textColor, NumberUtil.abbreviate(team.resourceDownloads()) + " downloads"));
        addComponent(teamParameters.getRatings().asTextComponent(textColor, NumberUtil.abbreviate(team.resourceRatings()) + " ratings"));

        return getComponents();
    }

    @Override
    public BufferedImage draw(BannerOutputFormat outputType) {
        ImageBuilder builder = ImageBuilder.create(teamParameters.getBackground().readTemplate().getImage(), outputType);

        for (BasicComponent component : build()) {
            builder = component.draw(builder, outputType);
        }

        return builder.build();
    }
}
