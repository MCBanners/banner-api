package com.mcbanners.backend.banner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public enum BannerTemplate {
    BLUE_RADIAL(BannerTextTheme.DARK),
    BURNING_ORANGE(BannerTextTheme.LIGHT),
    MANGO(BannerTextTheme.DARK),
    MOONLIGHT_PURPLE(BannerTextTheme.LIGHT),
    ORANGE_RADIAL(BannerTextTheme.DARK),
    VELVET(BannerTextTheme.DARK),
    YELLOW(BannerTextTheme.DARK);

    private final BannerTextTheme textTheme;

    BannerTemplate(BannerTextTheme textTheme) {
        this.textTheme = textTheme;
    }

    public BannerTextTheme getTextTheme() {
        return textTheme;
    }

    public String getFileName() {
        return name().toLowerCase().concat(".png");
    }

    public BufferedImage getImage() {
        InputStream stream = getClass().getResourceAsStream("/banner/" + getFileName());
        try {
            return ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BannerTemplate fromString(String name) {
        return valueOf(name.toUpperCase());
    }
}
