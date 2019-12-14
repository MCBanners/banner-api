package engineer.jacob.spigotbanners;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public enum BannerTemplate {
    BLUE(BannerTextTheme.DARK, BannerTextAlign.LEFT),
    ORANGE(BannerTextTheme.DARK, BannerTextAlign.LEFT),
    RED(BannerTextTheme.LIGHT, BannerTextAlign.LEFT),
    LIGHT_BLOCKS(BannerTextTheme.DARK, BannerTextAlign.RIGHT),
    ORANGE_BLOCKS(BannerTextTheme.DARK, BannerTextAlign.LEFT),
    DUAL_LIGHT_BLOCKS(BannerTextTheme.DARK, BannerTextAlign.CENTER);

    private final BannerTextTheme textTheme;
    private final BannerTextAlign textAlign;

    BannerTemplate(BannerTextTheme textTheme, BannerTextAlign textAlign) {
        this.textTheme = textTheme;
        this.textAlign = textAlign;
    }

    public BannerTextTheme getTextTheme() {
        return textTheme;
    }

    public BannerTextAlign getTextAlign() {
        return textAlign;
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
}
