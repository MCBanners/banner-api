package com.mcbanners.backend.image.layout;

import com.mcbanners.backend.banner.BannerSprite;
import com.mcbanners.backend.banner.param.author.AuthorParameter;
import com.mcbanners.backend.banner.param.author.AuthorParameterReader;
import com.mcbanners.backend.banner.param.author.AuthorTextParameterReader;
import com.mcbanners.backend.image.ImageBuilder;
import com.mcbanners.backend.image.component.Component;
import com.mcbanners.backend.image.component.LogoComponent;
import com.mcbanners.backend.obj.generic.Author;
import com.mcbanners.backend.util.NumberUtil;

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
    public List<Component> build() {
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

        AuthorTextParameterReader reviews = parameters.getRevCountParams();
        addComponent(reviews.makeComponent(textColor, NumberUtil.abbreviate(author.getRating()) + " reviews"));

        return getComponents();
    }

    @Override
    public BufferedImage draw() {
        ImageBuilder builder = ImageBuilder.create(parameters.getTemplate().getImage());

        for (Component component : build()) {
            builder = component.draw(builder);
        }

        return builder.build();
    }
}
