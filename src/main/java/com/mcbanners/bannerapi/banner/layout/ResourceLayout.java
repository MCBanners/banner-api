package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.banner.parameter.ResourceParameters;
import com.mcbanners.bannerapi.banner.parameter.api.namespace.SpaceableParameterNamespace;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.obj.generic.PriceInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.util.NumberUtil;

import java.awt.image.BufferedImage;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class ResourceLayout extends Layout<ResourceParameters> {
    private final Resource resource;
    private final Author author;
    private final ServiceBackend backend;
    private final String resourceTitle;


    public ResourceLayout(Resource resource, Author author, ServiceBackend backend, Map<String, String> rawParameters) {
        super(new ResourceParameters(rawParameters));

        this.resource = resource;
        this.author = author;
        this.backend = backend;

        String resourceTitle = parameters().getResourceName().readDisplay();
        if (resourceTitle.isEmpty() || resourceTitle.equalsIgnoreCase("unset")) {
            resourceTitle = resource.name();
        }

        this.resourceTitle = resourceTitle;
    }

    @Override
    public List<BasicComponent> build() {
        Sprite defaultLogoOverride = switch (backend) {
            case SPIGOT -> Sprite.DEFAULT_SPIGOT_RES_LOGO;
            case ORE -> Sprite.DEFAULT_SPONGE_RES_LOGO;
            case CURSEFORGE -> Sprite.DEFAULT_CURSEFORGE_RES_LOGO;
            case MODRINTH -> Sprite.DEFAULT_MODRINTH_RES_LOGO;
            case BUILTBYBIT -> Sprite.DEFAULT_BUILTBYBIT_RES_LOGO;
            case POLYMART -> Sprite.DEFAULT_POLYMART_RES_LOGO;
        };

        component(new LogoComponent(
                parameters().getLogo().readX(),
                parameters().getLogo().readSize(),
                resource.logo(),
                defaultLogoOverride
        ));

        text(parameters().getResourceName(), resourceTitle);

        text(parameters().getAuthorName(), "by %s", author.name());

        if (backend == ServiceBackend.CURSEFORGE || backend == ServiceBackend.MODRINTH) {
            date(parameters().getUpdated(), OffsetDateTime.parse(resource.lastUpdated()).toInstant(), "Updated:");
        } else {
            text(parameters().getReviews(), "%s reviews", NumberUtil.abbreviate(resource.rating().count()));
        }

        Double ratingAvg = resource.rating().averageRating();
        if (ratingAvg != null) {
            BufferedImage starFull = Sprite.STAR_FULL.getImage();
            BufferedImage starHalf = Sprite.STAR_HALF.getImage();
            BufferedImage starNone = Sprite.STAR_NONE.getImage();

            SpaceableParameterNamespace stars = parameters().getStars();
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
                    image(starsX + ((int) starsGap * i), starsY, toOverlay);
                }
            }
        }

        PriceInformation priceInfo = resource.price();

        boolean isPremium = priceInfo != null;
        String wording = isPremium ? "purchases" : "downloads";

        text(parameters().getDownloads(), "%s %s", NumberUtil.abbreviate(resource.downloadCount()), wording);

        if (isPremium) {
            text(parameters().getPrice(), "%.2f %s", priceInfo.amount(), priceInfo.currency());
        }

        return components();
    }
}
