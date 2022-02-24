package com.mcbanners.bannerapi.image.layout;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.banner.BannerSprite;
import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.banner.param.AuthorParameter;
import com.mcbanners.bannerapi.banner.param.ParameterReader;
import com.mcbanners.bannerapi.banner.param.TextParameterReader;
import com.mcbanners.bannerapi.image.ImageBuilder;
import com.mcbanners.bannerapi.image.component.BasicComponent;
import com.mcbanners.bannerapi.image.component.LogoComponent;
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
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final TextParameterReader<AuthorParameter> authorName;
    private final TextParameterReader<AuthorParameter> resourceCount;
    private final TextParameterReader<AuthorParameter> downloads;
    private final TextParameterReader<AuthorParameter> likes;
    private final TextParameterReader<AuthorParameter> reviews;

    public AuthorLayout(Author author, Map<String, String> parameters, ServiceBackend backend) {
        this.author = author;
        this.backend = backend;

        ParameterReader<AuthorParameter> reader = new ParameterReader<>(AuthorParameter.class, parameters);
        reader.addTextReaders("author_name", "resource_count", "likes", "downloads", "reviews");

        template = reader.getBannerTemplate();
        logoSize = reader.getLogoSize();
        logoX = reader.getLogoX();
        authorName = reader.getTextReader("author_name");
        resourceCount = reader.getTextReader("resource_count");
        likes = reader.getTextReader("likes");
        downloads = reader.getTextReader("downloads");
        reviews = reader.getTextReader("reviews");
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(template);

        addComponent(new LogoComponent(logoX, BannerSprite.DEFAULT_AUTHOR_LOGO, author.getIcon(), logoSize));
        addComponent(authorName.makeComponent(textColor, author.getName()));
        addComponent(resourceCount.makeComponent(textColor, author.getResources() + " resources"));
        if (author.getLikes() != -1) {
            addComponent(likes.makeComponent(textColor, NumberUtil.abbreviate(author.getLikes()) + (backend == ServiceBackend.MODRINTH ? " followers" : " likes")));
        }
        addComponent(downloads.makeComponent(textColor, NumberUtil.abbreviate(author.getDownloads()) + " downloads"));
        if (author.getRating() != -1) {
            addComponent(reviews.makeComponent(textColor, NumberUtil.abbreviate(author.getRating()) + " reviews"));
        }

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
