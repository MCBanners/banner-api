package engineer.jacob.spigotbanners;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public enum BannerTemplate {
    BLUE(BannerTextTheme.DARK),
    ORANGE(BannerTextTheme.DARK),
    RED(BannerTextTheme.LIGHT),
    LIGHT_BLOCKS(BannerTextTheme.DARK),
    ORANGE_BLOCKS(BannerTextTheme.DARK);

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
}
