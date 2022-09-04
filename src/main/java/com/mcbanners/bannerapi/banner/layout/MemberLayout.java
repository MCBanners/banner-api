package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.ImageBuilder;
import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.banner.parameter.MemberParameters;
import com.mcbanners.bannerapi.obj.generic.Member;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.util.NumberUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class MemberLayout extends Layout {
    private final Member member;
    private final ServiceBackend backend;
    private final MemberParameters memberParameters;

    public MemberLayout(Member member, ServiceBackend backend, Map<String, String> rawParameters) {
        this.member = member;
        this.backend = backend;
        this.memberParameters = new MemberParameters(rawParameters);
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(memberParameters.getBackground().readTemplate());

        addComponent(new LogoComponent(
                memberParameters.getLogo().readX(),
                memberParameters.getLogo().readSize(),
                member.icon(),
                Sprite.DEFAULT_AUTHOR_LOGO
        ));

        addComponent(memberParameters.getMemberName().asTextComponent(textColor, member.name()));

        addComponent(memberParameters.getRank().asTextComponent(textColor, "Rank: " + member.rank()));

        addComponent(memberParameters.getJoined().asTextComponent(textColor, "Joined: " + member.joinDate()));
        addComponent(memberParameters.getPosts().asTextComponent(textColor, "Posts: " + NumberUtil.abbreviate(member.posts())));

        int calculated = member.positiveFeedback() - member.negativeFeedback();
        String sign;
        if (calculated > 0) {
            sign = "+";
        } else if (calculated < 0) {
            sign = "-";
        } else {
            sign = "";
        }

        addComponent(memberParameters.getLikes().asTextComponent(textColor, "Feedback: " + sign + calculated));

        return getComponents();
    }

    @Override
    public BufferedImage draw(BannerOutputFormat outputType) {
        ImageBuilder builder = ImageBuilder.create(memberParameters.getBackground().readTemplate().getImage(), outputType);

        for (BasicComponent component : build()) {
            builder = component.draw(builder, outputType);
        }

        return builder.build();
    }
}
