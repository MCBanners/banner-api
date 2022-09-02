package com.mcbanners.bannerapi.image.layout;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.banner.BannerSprite;
import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.banner.param.MemberParameter;
import com.mcbanners.bannerapi.banner.param.ParameterReader;
import com.mcbanners.bannerapi.banner.param.TextParameterReader;
import com.mcbanners.bannerapi.image.ImageBuilder;
import com.mcbanners.bannerapi.image.component.BasicComponent;
import com.mcbanners.bannerapi.image.component.LogoComponent;
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
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final TextParameterReader<MemberParameter> memberName;
    private final TextParameterReader<MemberParameter> rank;
    private final TextParameterReader<MemberParameter> joined;
    private final TextParameterReader<MemberParameter> posts;
    private final TextParameterReader<MemberParameter> likes;

    public MemberLayout(Member member, Map<String, String> parameters, ServiceBackend backend) {
        this.member = member;
        this.backend = backend;

        ParameterReader<MemberParameter> reader = new ParameterReader<>(MemberParameter.class, parameters);
        reader.addTextReaders("member_name", "rank", "joined", "posts", "likes");

        template = reader.getBannerTemplate();
        logoSize = reader.getLogoSize();
        logoX = reader.getLogoX();
        memberName = reader.getTextReader("member_name");
        rank = reader.getTextReader("rank");
        joined = reader.getTextReader("joined");
        posts = reader.getTextReader("posts");
        likes = reader.getTextReader("likes");
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(template);

        addComponent(new LogoComponent(logoX, BannerSprite.DEFAULT_AUTHOR_LOGO, member.icon(), logoSize));
        addComponent(memberName.makeComponent(textColor, member.name()));

        addComponent(rank.makeComponent(textColor, "Rank: " + member.rank()));

        addComponent(joined.makeComponent(textColor, "Joined: " + member.joinDate()));
        addComponent(posts.makeComponent(textColor, "Posts: " + NumberUtil.abbreviate(member.posts())));

        int calculated = member.positiveFeedback() - member.negativeFeedback();
        String sign;
        if (calculated > 0) {
            sign = "+";
        } else if (calculated < 0) {
            sign = "-";
        } else {
            sign = "";
        }
        addComponent(likes.makeComponent(textColor, "Feedback: " + sign + calculated));

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
