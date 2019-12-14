package engineer.jacob.spigotbanners.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageBuilder {
    private final BufferedImage image;

    private ImageBuilder(BufferedImage base) {
        int width = base.getWidth(), height = base.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(base, 0, 0, width, height, null);
        graphics.dispose();

        this.image = image;
    }

    public ImageTextBuilder text() {
        return ImageTextBuilder.create(this);
    }

    public BufferedImage build() {
        return image;
    }

    public static ImageBuilder create(BufferedImage image) {
        return new ImageBuilder(image);
    }
}
