package com.mcbanners.bannerapi.image.layout;

import com.mcbanners.bannerapi.banner.BannerSprite;
import com.mcbanners.bannerapi.banner.param.resource.ResourceParameter;
import com.mcbanners.bannerapi.banner.param.resource.ResourceParameterReader;
import com.mcbanners.bannerapi.banner.param.resource.ResourceTextParameterReader;
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
    private final String resourceName;
    private final ResourceParameterReader parameters;
    private final ServiceBackend backend;

    public ResourceLayout(Resource resource, Author author, Map<ResourceParameter, Object> parameters, ServiceBackend backend) {
        this.resource = resource;
        this.author = author;

        String resourceName = (String) parameters.get(ResourceParameter.RES_NAME_DISPLAY);
        if (resourceName.isEmpty()) {
            resourceName = resource.getName();
        }

        this.resourceName = resourceName;
        this.parameters = new ResourceParameterReader(parameters);
        this.backend = backend;
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(parameters.getTemplate());

        BannerSprite defaultLogoOverride;
        switch (backend) {
            case SPIGET:
                defaultLogoOverride = BannerSprite.DEFAULT_SPIGOT_RES_LOGO;
                break;
            case ORE:
                defaultLogoOverride = BannerSprite.DEFAULT_SPONGE_RES_LOGO;
                break;
            default:
                throw new RuntimeException("not yet implemented");
        }

        addComponent(new LogoComponent(parameters.getLogoX(), defaultLogoOverride, resource.getLogo(), parameters.getLogoSize()));

        ResourceTextParameterReader name = parameters.getResNameParams();
        addComponent(name.makeComponent(textColor, resourceName));

        ResourceTextParameterReader author = parameters.getAutNameParams();
        addComponent(author.makeComponent(textColor, String.format("by %s", this.author.getName())));

        ResourceTextParameterReader reviews = parameters.getRevCountParams();
        addComponent(reviews.makeComponent(textColor, NumberUtil.abbreviate(resource.getRating().getCount()) + " reviews"));

        BufferedImage starFull = BannerSprite.STAR_FULL.getImage();
        BufferedImage starHalf = BannerSprite.STAR_HALF.getImage();
        BufferedImage starNone = BannerSprite.STAR_NONE.getImage();

        Double ratingAvg = resource.getRating().getAverageRating();
        if (ratingAvg != null) {
            double gap = parameters.getStarsGap();

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

                addComponent(new ImageComponent(
                        parameters.getStarsX() + ((int) gap * i),
                        parameters.getStarsY(),
                        toOverlay
                ));
            }
        }

        ResourceTextParameterReader downloads = parameters.getDlCountParams();
        addComponent(downloads.makeComponent(textColor, NumberUtil.abbreviate(resource.getDownloadCount()) + " downloads"));

        PriceInformation priceInfo = resource.getPrice();
        if (priceInfo != null) {
            ResourceTextParameterReader price = parameters.getPriceParams();
            addComponent(price.makeComponent(
                    textColor,
                    String.format("%.2f %s", priceInfo.getAmount(), priceInfo.getCurrency())
            ));
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
