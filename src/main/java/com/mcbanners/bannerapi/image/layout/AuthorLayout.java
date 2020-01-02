package com.mcbanners.bannerapi.image.layout;

import com.mcbanners.bannerapi.banner.BannerSprite;
import com.mcbanners.bannerapi.banner.param.author.AuthorParameter;
import com.mcbanners.bannerapi.banner.param.author.AuthorParameterReader;
import com.mcbanners.bannerapi.banner.param.author.AuthorTextParameterReader;
import com.mcbanners.bannerapi.image.ImageBuilder;
import com.mcbanners.bannerapi.image.component.BasicComponent;
import com.mcbanners.bannerapi.image.component.LogoComponent;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.util.NumberUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class AuthorLayout extends Layout {
    private final Author author;
    private final AuthorParameterReader parameters;

    public AuthorLayout(Author author, Map<AuthorParameter, Object> parameters) {
        this.author = author;
        this.parameters = new AuthorParameterReader(parameters);
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(parameters.getTemplate());

        addComponent(new LogoComponent(parameters.getLogoX(), BannerSprite.DEFAULT_AUTHOR_LOGO, author.getIcon(), parameters.getLogoSize()));

        AuthorTextParameterReader name = parameters.getAutNameParams();
        addComponent(name.makeComponent(textColor, author.getName()));

        AuthorTextParameterReader res = parameters.getResAmountParams();
        addComponent(res.makeComponent(textColor, author.getResources() + " resources"));

        AuthorTextParameterReader downloads = parameters.getDlCountParams();
        addComponent(downloads.makeComponent(textColor, NumberUtil.abbreviate(author.getDownloads()) + " downloads"));

        AuthorTextParameterReader likes = parameters.getLikesCountParam();
        addComponent(likes.makeComponent(textColor, NumberUtil.abbreviate(author.getLikes()) + " likes"));

        if (author.getRating() != -1) {
            AuthorTextParameterReader reviews = parameters.getRevCountParams();
            addComponent(reviews.makeComponent(textColor, NumberUtil.abbreviate(author.getRating()) + " reviews"));
        }

        return getComponents();
    }

    @Override
    public BufferedImage draw() {
        ImageBuilder builder = ImageBuilder.create(parameters.getTemplate().getImage());

        for (BasicComponent component : build()) {
            builder = component.draw(builder);
        }

        return builder.build();
    }
}
