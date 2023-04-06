package com.mcbanners.bannerapi.banner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public enum BackgroundTemplate {
    BLUE_RADIAL(TextTheme.DARK),
    BURNING_ORANGE(TextTheme.LIGHT),
    MANGO(TextTheme.DARK),
    MOONLIGHT_PURPLE(TextTheme.LIGHT),
    ORANGE_RADIAL(TextTheme.DARK),
    VELVET(TextTheme.DARK),
    YELLOW(TextTheme.DARK),
    MALACHITE_GREEN(TextTheme.DARK),
    DARK_GUNMETAL(TextTheme.LIGHT),
    PURPLE_TAUPE(TextTheme.LIGHT),
    LIGHT_MODE(TextTheme.DARK);


    private final TextTheme textTheme;

    BackgroundTemplate(TextTheme textTheme) {
        this.textTheme = textTheme;
    }

    public TextTheme getTextTheme() {
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

    public static BackgroundTemplate fromString(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
