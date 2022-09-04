package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.banner.parameter.MemberParameters;
import com.mcbanners.bannerapi.obj.generic.Member;
import com.mcbanners.bannerapi.util.NumberUtil;

import java.util.List;
import java.util.Map;

public class MemberLayout extends Layout<MemberParameters> {
    private final Member member;

    public MemberLayout(Member member, Map<String, String> rawParameters) {
        super(new MemberParameters(rawParameters));

        this.member = member;
    }

    @Override
    public List<BasicComponent> build() {
        component(new LogoComponent(
                parameters().getLogo().readX(),
                parameters().getLogo().readSize(),
                member.icon(),
                Sprite.DEFAULT_AUTHOR_LOGO
        ));

        text(parameters().getMemberName(), member.name());

        text(parameters().getRank(), "Rank: %s", member.rank());

        text(parameters().getJoined(), "Joined: %s", member.joinDate());

        text(parameters().getPosts(), "Posts: %s", NumberUtil.abbreviate(member.posts()));

        int calculated = member.positiveFeedback() - member.negativeFeedback();
        String sign;
        if (calculated > 0) {
            sign = "+";
        } else if (calculated < 0) {
            sign = "-";
        } else {
            sign = "";
        }

        text(parameters().getLikes(), "Feedback: %s", sign + calculated);

        return components();
    }
}
