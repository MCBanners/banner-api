package com.mcbanners.bannerapi.util;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.TextAlign;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {
    public static Graphics2D setRenderOpts(Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        return graphics;
    }

    public static BufferedImage resize(BufferedImage original, BannerOutputFormat outputType, int newWidth, int newHeight) {
        Image temp = original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        int colorDepth = BufferedImage.TYPE_INT_ARGB;
        if (outputType == BannerOutputFormat.JPEG) {
            colorDepth = BufferedImage.TYPE_INT_RGB;
        }

        BufferedImage resized = new BufferedImage(newWidth, newHeight, colorDepth);
        Graphics2D graphics = setRenderOpts(resized.createGraphics());
        graphics.drawImage(temp, 0, 0, null);
        graphics.dispose();

        return resized;
    }

    public static void drawText(BufferedImage source, Graphics2D graphics, String text, int x, int y, TextAlign textAlign) {
        FontMetrics fontMetrics = graphics.getFontMetrics();

        int textX = x;
        switch (textAlign) {
            case LEFT -> graphics.drawString(text, textX, y);

            case CENTER -> {
                textX = source.getMinX() + (source.getWidth() - fontMetrics.stringWidth(text)) / 2;
                graphics.drawString(text, textX + x, y);
            }

            case RIGHT -> {
                textX = source.getWidth() - fontMetrics.stringWidth(text);
                graphics.drawString(text, textX + x, y);
            }
        }
    }
}
