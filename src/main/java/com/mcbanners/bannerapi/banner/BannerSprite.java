package com.mcbanners.bannerapi.banner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public enum BannerSprite {
    STAR_FULL,
    STAR_HALF,
    STAR_NONE,
    DEFAULT_SPIGOT_RES_LOGO,
    DEFAULT_SPONGE_RES_LOGO,
    DEFAULT_CURSEFORGE_RES_LOGO,
    DEFAULT_MODRINTH_RES_LOGO,
    DEFAULT_POLYMART_RES_LOGO,
    DEFAULT_MCMARKET_RES_LOGO,
    DEFAULT_AUTHOR_LOGO,
    DEFAULT_SERVER_LOGO;

    public String getFileName() {
        return name().toLowerCase().concat(".png");
    }

    public BufferedImage getImage() {
        InputStream stream = getClass().getResourceAsStream("/sprites/" + getFileName());
        try {
            return ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
