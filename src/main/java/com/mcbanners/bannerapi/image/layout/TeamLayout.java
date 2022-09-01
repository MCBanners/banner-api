package com.mcbanners.bannerapi.image.layout;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.banner.BannerSprite;
import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.banner.param.ParameterReader;
import com.mcbanners.bannerapi.banner.param.TeamParameter;
import com.mcbanners.bannerapi.banner.param.TextParameterReader;
import com.mcbanners.bannerapi.image.ImageBuilder;
import com.mcbanners.bannerapi.image.component.BasicComponent;
import com.mcbanners.bannerapi.image.component.LogoComponent;
import com.mcbanners.bannerapi.obj.generic.Team;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.util.NumberUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class TeamLayout extends Layout {
    private final Team team;
    private final ServiceBackend backend;
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final TextParameterReader<TeamParameter> teamName;
    private final TextParameterReader<TeamParameter> resourceCount;
    private final TextParameterReader<TeamParameter> downloads;
    private final TextParameterReader<TeamParameter> ratings;

    public TeamLayout(Team team, Map<String, String> parameters, ServiceBackend backend) {
        this.team = team;
        this.backend = backend;

        ParameterReader<TeamParameter> reader = new ParameterReader<>(TeamParameter.class, parameters);
        reader.addTextReaders("team_name", "resource_count", "downloads", "ratings");

        template = reader.getBannerTemplate();
        logoSize = reader.getLogoSize();
        logoX = reader.getLogoX();
        teamName = reader.getTextReader("team_name");
        resourceCount = reader.getTextReader("resource_count");
        downloads = reader.getTextReader("downloads");
        ratings = reader.getTextReader("ratings");
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(template);

        addComponent(new LogoComponent(logoX, BannerSprite.DEFAULT_POLYMART_RES_LOGO, team.icon(), logoSize));
        addComponent(teamName.makeComponent(textColor, team.name()));
        addComponent(resourceCount.makeComponent(textColor, team.resourceCount() + " resources"));
        addComponent(downloads.makeComponent(textColor, NumberUtil.abbreviate(team.resourceDownloads()) + " downloads"));
        addComponent(ratings.makeComponent(textColor, NumberUtil.abbreviate(team.resourceRatings()) + " ratings"));

        return getComponents();
    }

    @Override
    public BufferedImage draw(BannerOutputType outputType) {
        ImageBuilder builder = ImageBuilder.create(template.getImage(), outputType);

        for (BasicComponent component : build()) {
            builder = component.draw(builder, outputType);
        }

        return builder.build();
    }
}
