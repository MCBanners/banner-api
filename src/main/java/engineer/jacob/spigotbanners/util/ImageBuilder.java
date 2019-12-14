package engineer.jacob.spigotbanners.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageBuilder {
    private final BufferedImage image;

    private ImageBuilder(BufferedImage base) {
        int width = base.getWidth(), height = base.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(base, 0, 0, width, height, null);
        graphics.dispose();

        this.image = image;
    }

    public ImageBuilder overlayImage(BufferedImage overlay, int x, int y) {
        Graphics2D graphics = image.createGraphics();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        graphics.drawImage(overlay, x, y, overlay.getWidth(), overlay.getHeight(), null);
        graphics.dispose();

        return this;
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
