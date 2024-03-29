package com.mcbanners.bannerapi.banner.component;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.ImageBuilder;
import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class LogoComponent extends BasicComponent {
    private final Sprite defaultOverride;
    private final String base64Image;
    private final int requestedLogoSize;
    private final int maxLogoSize;

    public LogoComponent(int x, int requestedLogoSize, String base64Image, Sprite defaultOverride) {
        this(x, requestedLogoSize, 96, base64Image, defaultOverride);
    }

    public LogoComponent(int x, int requestedLogoSize, int maxLogoSize, String base64Image, Sprite defaultOverride) {
        super(x, -1); // y is calculated based on determined logo size

        this.requestedLogoSize = requestedLogoSize;
        this.maxLogoSize = maxLogoSize;
        this.base64Image = base64Image;
        this.defaultOverride = defaultOverride;
    }

    @Override
    public ImageBuilder draw(ImageBuilder builder, BannerOutputFormat outputType) {
        BufferedImage logo = defaultOverride.getImage();

        try {
            if (base64Image != null && !base64Image.isEmpty()) {
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
