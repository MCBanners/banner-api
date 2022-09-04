package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.parameter.AuthorParameters;
import com.mcbanners.bannerapi.banner.ImageBuilder;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.util.NumberUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class AuthorLayout extends Layout {
    private final Author author;
    private final ServiceBackend backend;
    private final AuthorParameters authorParameters;

    public AuthorLayout(Author author, ServiceBackend backend, Map<String, String> rawParameters) {
        this.author = author;
        this.backend = backend;
        this.authorParameters = new AuthorParameters(rawParameters);
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(authorParameters.getBackground().readTemplate());

        addComponent(new LogoComponent(
                authorParameters.getLogo().readX(),
                authorParameters.getLogo().readSize(),
                author.icon(),
                Sprite.DEFAULT_AUTHOR_LOGO
        ));

        addComponent(authorParameters.getAuthorName().asTextComponent(textColor, author.name()));

        addComponent(authorParameters.getResourceCount().asTextComponent(textColor, author.resources() + " resources"));

        if (author.likes() != -1) {
            addComponent(authorParameters.getLikes().asTextComponent(textColor, NumberUtil.abbreviate(author.likes()) + (backend == ServiceBackend.MODRINTH ? " followers" : " likes")));
        }

        addComponent(authorParameters.getDownloads().asTextComponent(textColor, NumberUtil.abbreviate(author.downloads()) + " downloads"));

        if (author.rating() != -1) {
            addComponent(authorParameters.getReviews().asTextComponent(textColor, NumberUtil.abbreviate(author.rating()) + " reviews"));
        }

        return getComponents();
    }

    @Override
    public BufferedImage draw(BannerOutputFormat outputType) {
        ImageBuilder builder = ImageBuilder.create(authorParameters.getBackground().readTemplate().getImage(), outputType);

        for (BasicComponent component : build()) {
            builder = component.draw(builder, outputType);
        }

        return builder.build();
    }
}
