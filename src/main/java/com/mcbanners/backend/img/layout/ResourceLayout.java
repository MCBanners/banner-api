package com.mcbanners.backend.img.layout;

import com.mcbanners.backend.banner.BannerSprite;
import com.mcbanners.backend.banner.param.TextParameterReader;
import com.mcbanners.backend.banner.param.res.ResourceParameter;
import com.mcbanners.backend.banner.param.res.ResourceParameterReader;
import com.mcbanners.backend.img.ImageBuilder;
import com.mcbanners.backend.img.component.Component;
import com.mcbanners.backend.img.component.ImageComponent;
import com.mcbanners.backend.obj.Author;
import com.mcbanners.backend.obj.PriceInformation;
import com.mcbanners.backend.obj.Resource;
import com.mcbanners.backend.util.ImageUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class ResourceLayout extends Layout {
    private final Resource resource;
    private final Author author;
    private final String resourceName;
    private final ResourceParameterReader parameters;

    @Value("${banner.dark_text_color}")
    private int[] darkTextRgb;
    @Value("${banner.light_text_color}")
    private int[] lightTextRgb;

    public ResourceLayout(Resource resource, Author author, Map<ResourceParameter, Object> parameters) {
        this.resource = resource;
        this.author = author;

        String resourceName = (String) parameters.get(ResourceParameter.RES_NAME_DISPLAY);
        if (resourceName.isEmpty()) {
            resourceName = resource.getName();
        }

        this.resourceName = resourceName;

        this.parameters = new ResourceParameterReader(parameters);
    }

    public List<Component> getComponents() {
        List<Component> components = new ArrayList<>();
        Color textColor = getTextColor(parameters.getTemplate());

        try {
            BufferedImage resourceLogo = BannerSprite.DEFAULT_SPIGOT_RES_LOGO.getImage();

            String logo = resource.getLogo();
            if (!logo.isEmpty()) {
                BufferedImage temp = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(resource.getLogo())));
                if (temp != null) {
                    resourceLogo = temp;
                }
            }

            if (resourceLogo == null) {
                // literally everything failed including fallback logo if we're in here
                throw new RuntimeException("Could not load real or fallback logo for banner, giving up");
            }

            int resourceLogoSize = Math.min(parameters.getLogoSize(), 96);
            if (resourceLogoSize < 96) {
                resourceLogo = ImageUtil.resize(resourceLogo, resourceLogoSize, resourceLogoSize);
            }

            components.add(new ImageComponent(
                    parameters.getLogoX(),
                    (100 - resourceLogoSize) / 2,
                    resourceLogo
            ));
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
        }

        TextParameterReader name = parameters.getResNameParams();
        components.add(name.makeComponent(textColor, resourceName));

        TextParameterReader author = parameters.getAutNameParams();
        components.add(author.makeComponent(textColor, String.format("by %s", this.author.getName())));

        TextParameterReader reviews = parameters.getRevCountParams();
        components.add(reviews.makeComponent(textColor, String.format("%d reviews", resource.getRating().getCount())));

        BufferedImage starFull = BannerSprite.STAR_FULL.getImage();
        BufferedImage starHalf = BannerSprite.STAR_HALF.getImage();
        BufferedImage starNone = BannerSprite.STAR_NONE.getImage();

        double ratingAvg = resource.getRating().getAverageRating();
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

            components.add(new ImageComponent(
                    parameters.getStarsX() + ((int) gap * i),
                    parameters.getStarsY(),
                    toOverlay
            ));
        }

        TextParameterReader downloads = parameters.getDlCountParams();
        components.add(downloads.makeComponent(textColor, String.format("%d downloads", resource.getDownloadCount())));

        PriceInformation priceInfo = resource.getPrice();
        if (priceInfo != null) {
            TextParameterReader price = parameters.getPriceParams();
            components.add(price.makeComponent(
                    textColor,
                    String.format("%.2f %s", priceInfo.getAmount(), priceInfo.getCurrency())
            ));
        }

        return components;
    }

    @Override
    public BufferedImage draw() {
        ImageBuilder builder = ImageBuilder.create(parameters.getTemplate().getImage());

        for (Component component : getComponents()) {
            builder = component.draw(builder);
        }

        return builder.build();
    }
}
