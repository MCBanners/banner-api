package com.mcbanners.bannerapi.image.layout;

import com.mcbanners.bannerapi.banner.BannerSprite;
import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.banner.param.ParameterReader;
import com.mcbanners.bannerapi.banner.param.ResourceParameter;
import com.mcbanners.bannerapi.banner.param.TextParameterReader;
import com.mcbanners.bannerapi.image.ImageBuilder;
import com.mcbanners.bannerapi.image.component.BasicComponent;
import com.mcbanners.bannerapi.image.component.ImageComponent;
import com.mcbanners.bannerapi.image.component.LogoComponent;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.obj.generic.PriceInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.util.NumberUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class ResourceLayout extends Layout {
    private final Resource resource;
    private final Author author;
    private final ServiceBackend backend;
    private final String resourceTitle;
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final TextParameterReader<ResourceParameter> resourceName;
    private final TextParameterReader<ResourceParameter> authorName;
    private final TextParameterReader<ResourceParameter> reviews;
    private final double starsGap;
    private final int starsX;
    private final int starsY;
    private final TextParameterReader<ResourceParameter> downloads;
    private final TextParameterReader<ResourceParameter> price;


    public ResourceLayout(Resource resource, Author author, Map<String, String> parameters, ServiceBackend backend) {
        this.resource = resource;
        this.author = author;
        this.backend = backend;

        ParameterReader<ResourceParameter> reader = new ParameterReader<>(ResourceParameter.class, parameters);
        reader.addTextReaders("resource_name", "author_name", "reviews", "downloads", "price");

        String resourceTitle = (String) reader.getOrDefault(ResourceParameter.RESOURCE_NAME_DISPLAY);
        if (resourceTitle.isEmpty() || resourceTitle.equalsIgnoreCase("unset")) {
            resourceTitle = resource.getName();
        }

        this.resourceTitle = resourceTitle;
        template = reader.getBannerTemplate();
        logoSize = reader.getLogoSize();
        logoX = reader.getLogoX();
        resourceName = reader.getTextReader("resource_name");
        authorName = reader.getTextReader("author_name");
        reviews = reader.getTextReader("reviews");
        starsGap = (double) reader.getOrDefault(ResourceParameter.STARS_GAP);
        starsX = (int) reader.getOrDefault(ResourceParameter.STARS_X);
        starsY = (int) reader.getOrDefault(ResourceParameter.STARS_Y);
        downloads = reader.getTextReader("downloads");
        price = reader.getTextReader("price");
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(template);

        BannerSprite defaultLogoOverride;
        switch (backend) {
            case SPIGOT:
                defaultLogoOverride = BannerSprite.DEFAULT_SPIGOT_RES_LOGO;
                break;
            case ORE:
                defaultLogoOverride = BannerSprite.DEFAULT_SPONGE_RES_LOGO;
                break;
            default:
                throw new RuntimeException("not yet implemented");
        }

        addComponent(new LogoComponent(logoX, defaultLogoOverride, resource.getLogo(), logoSize));
        addComponent(resourceName.makeComponent(textColor, resourceTitle));
        addComponent(authorName.makeComponent(textColor, String.format("by %s", this.author.getName())));
        addComponent(reviews.makeComponent(textColor, NumberUtil.abbreviate(resource.getRating().getCount()) + " reviews"));

        BufferedImage starFull = BannerSprite.STAR_FULL.getImage();
        BufferedImage starHalf = BannerSprite.STAR_HALF.getImage();
        BufferedImage starNone = BannerSprite.STAR_NONE.getImage();

        Double ratingAvg = resource.getRating().getAverageRating();
        if (ratingAvg != null) {
            for (int i = 0; i < 5; i++) {
                BufferedImage toOverlay;

                if (ratingAvg >= 1) {
                    ratingAvg--;
                    toOverlay = starFull;
                } else if (ratingAvg >= 0.5) {
                    ratingAvg -= 0.5;
                    toOverlay = starHalf;
                } else {
                    toOverlay = starNone;
                }

                addComponent(new ImageComponent(starsX + ((int) starsGap * i), starsY, toOverlay));
            }
        }

        addComponent(downloads.makeComponent(textColor, NumberUtil.abbreviate(resource.getDownloadCount()) + " downloads"));

        PriceInformation priceInfo = resource.getPrice();
        if (priceInfo != null) {
            addComponent(price.makeComponent(
                    textColor,
                    String.format("%.2f %s", priceInfo.getAmount(), priceInfo.getCurrency())
            ));
        }

        return getComponents();
    }

    @Override
    public BufferedImage draw() {
        ImageBuilder builder = ImageBuilder.create(template.getImage());

        for (BasicComponent component : build()) {
            builder = component.draw(builder);
        }

        return builder.build();
    }
}
