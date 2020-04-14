package com.mcbanners.bannerapi.image.component;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.banner.BannerSprite;
import com.mcbanners.bannerapi.image.ImageBuilder;
import com.mcbanners.bannerapi.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class LogoComponent extends BasicComponent {
    private final BannerSprite defaultOverride;
    private final String base64Image;
    private final int requestedLogoSize;
    private final int maxLogoSize;

    public LogoComponent(int x, BannerSprite defaultOverride, String base64Image, int requestedLogoSize) {
        this(x, defaultOverride, base64Image, requestedLogoSize, 96);
    }

    public LogoComponent(int x, BannerSprite defaultOverride, String base64Image, int requestedLogoSize, int maxLogoSize) {
        super(x, -1); // y is calculated based on determined logo size

        this.defaultOverride = defaultOverride;
        this.base64Image = base64Image;
        this.requestedLogoSize = requestedLogoSize;
        this.maxLogoSize = maxLogoSize;
    }

    @Override
    public ImageBuilder draw(ImageBuilder builder, BannerOutputType outputType) {
        BufferedImage logo = defaultOverride.getImage();

        try {
            if (!base64Image.isEmpty()) {
                BufferedImage temp = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64Image)));
                if (temp != null) {
                    logo = temp;
                }
            }

            if (logo == null) {
                throw new RuntimeException("Could not load real or fallback image for LogoComponent!");
            }

            int logoSize = Math.min(requestedLogoSize, maxLogoSize);
            if (logoSize < maxLogoSize) {
                logo = ImageUtil.resize(logo, outputType, logoSize, logoSize);
            }

            return new ImageComponent(x, (100 - logoSize) / 2, logo).draw(builder, outputType);
        } catch (IOException | RuntimeException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
