package com.mcbanners.bannerapi.image.component;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.image.ImageBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageComponent extends BasicComponent {
    private final BufferedImage overlay;

    public ImageComponent(int x, int y, String b64) throws IOException {
        this(x, y, Base64.getDecoder().decode(b64));
    }

    public ImageComponent(int x, int y, byte[] b64) throws IOException {
        this(x, y, new ByteArrayInputStream(b64));
    }

    public ImageComponent(int x, int y, ByteArrayInputStream overlay) throws IOException {
        this(x, y, ImageIO.read(overlay));
    }

    public ImageComponent(int x, int y, BufferedImage overlay) {
        super(x, y);
        this.overlay = overlay;
    }

    @Override
    public ImageBuilder draw(ImageBuilder builder, BannerOutputType outputType) {
        return builder.overlayImage(this.overlay, this.x, this.y);
    }
}
