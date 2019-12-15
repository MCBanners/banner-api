package engineer.jacob.spigotbanners;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public enum BannerTemplate {
    BLACK_BRICK(BannerTextTheme.LIGHT),
    WOOD(BannerTextTheme.LIGHT),
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
}
