package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.ImageBuilder;
import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.ImageComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.banner.parameter.ResourceParameters;
import com.mcbanners.bannerapi.banner.parameter.api.namespace.SpaceableParameterNamespace;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.obj.generic.PriceInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.util.NumberUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ResourceLayout extends Layout {
    private final Resource resource;
    private final Author author;
    private final ServiceBackend backend;
    private final ResourceParameters resourceParameters;
    private final String resourceTitle;


    public ResourceLayout(Resource resource, Author author, ServiceBackend backend, Map<String, String> rawParameters) {
        this.resource = resource;
        this.author = author;
        this.backend = backend;
        this.resourceParameters = new ResourceParameters(rawParameters);

        String resourceTitle = resourceParameters.getResourceName().readDisplay();
        if (resourceTitle.isEmpty() || resourceTitle.equalsIgnoreCase("unset")) {
            resourceTitle = resource.name();
        }

        this.resourceTitle = resourceTitle;
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(resourceParameters.getBackground().readTemplate());

        Sprite defaultLogoOverride = switch (backend) {
            case SPIGOT -> Sprite.DEFAULT_SPIGOT_RES_LOGO;
            case ORE -> Sprite.DEFAULT_SPONGE_RES_LOGO;
            case CURSEFORGE -> Sprite.DEFAULT_CURSEFORGE_RES_LOGO;
            case MODRINTH -> Sprite.DEFAULT_MODRINTH_RES_LOGO;
            case BUILTBYBIT -> Sprite.DEFAULT_BUILTBYBIT_RES_LOGO;
            case POLYMART -> Sprite.DEFAULT_POLYMART_RES_LOGO;
        };

        addComponent(new LogoComponent(
                resourceParameters.getLogo().readX(),
                resourceParameters.getLogo().readSize(),
                resource.logo(),
                defaultLogoOverride
        ));

        addComponent(resourceParameters.getResourceName().asTextComponent(textColor, resourceTitle));

        addComponent(resourceParameters.getAuthorName().asTextComponent(textColor, String.format("by %s", this.author.name())));

        if (backend == ServiceBackend.CURSEFORGE || backend == ServiceBackend.MODRINTH) {
            Date date = Date.from(OffsetDateTime.parse(resource.lastUpdated()).toInstant());
            SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            addComponent(resourceParameters.getUpdated().asTextComponent(textColor, "Updated: " + sdf.format(date)));
        } else {
            addComponent(resourceParameters.getReviews().asTextComponent(textColor, NumberUtil.abbreviate(resource.rating().count()) + " reviews"));
        }


        Double ratingAvg = resource.rating().averageRating();
        if (ratingAvg != null) {
            BufferedImage starFull = Sprite.STAR_FULL.getImage();
            BufferedImage starHalf = Sprite.STAR_HALF.getImage();
            BufferedImage starNone = Sprite.STAR_NONE.getImage();

            SpaceableParameterNamespace stars = resourceParameters.getStars();
            int starsX = stars.readX(), starsY = stars.readY();
            double starsGap = stars.readGap();

            for (int i = 0; i < 5; i++) {
                BufferedImage toOverlay;

                if (ratingAvg >= 1) {
                    ratingAvg--;
                    toOverlay = starFull;
                } else if (ratingAvg >= 0.75) {
                    ratingAvg -= 0.75;
                    toOverlay = starFull;
                } else if (ratingAvg >= 0.25) {
                    ratingAvg -= 0.5;
                    toOverlay = starHalf;
                } else {
                    toOverlay = starNone;
                }

                if (backend != ServiceBackend.CURSEFORGE && backend != ServiceBackend.MODRINTH) {
                    addComponent(new ImageComponent(starsX + ((int) starsGap * i), starsY, toOverlay));
                }
            }
        }

        PriceInformation priceInfo = resource.price();
        boolean isPremium = priceInfo != null;

        addComponent(resourceParameters.getDownloads().asTextComponent(textColor, NumberUtil.abbreviate(resource.downloadCount()) + " " + (isPremium ? "purchases" : "downloads")));

        if (isPremium) {
            addComponent(resourceParameters.getPrice().asTextComponent(
                    textColor,
                    String.format("%.2f %s", priceInfo.amount(), priceInfo.currency())
            ));
        }

        return getComponents();
    }

    @Override
    public BufferedImage draw(BannerOutputFormat outputType) {
        ImageBuilder builder = ImageBuilder.create(resourceParameters.getBackground().readTemplate().getImage(), outputType);

        for (BasicComponent component : build()) {
            builder = component.draw(builder, outputType);
        }

        return builder.build();
    }
}
