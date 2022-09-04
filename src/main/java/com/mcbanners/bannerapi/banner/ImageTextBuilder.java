package com.mcbanners.bannerapi.banner;

import com.mcbanners.bannerapi.util.ImageUtil;
import com.mcbanners.bannerapi.util.StringUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageTextBuilder {
    private final ImageBuilder builder;
    private String text;
    private FontFace font;
    private Color color = Color.BLACK;
    private boolean bold = false;
    private Float fontSize = 12F;
    private TextWrapSettings wrapSettings;
    private TextAlign textAlign = TextAlign.LEFT;
    private int initialX = 0;
    private int initialY = 0;

    private ImageTextBuilder(ImageBuilder builder) {
        this.builder = builder;
    }

    public ImageTextBuilder content(String text) {
        return content(text, FontFace.SOURCE_SANS_PRO);
    }

    public ImageTextBuilder content(String text, FontFace font) {
        this.text = text;
        this.font = font;
        return this;
    }

    public ImageTextBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public ImageTextBuilder bold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public ImageTextBuilder fontSize(float fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public ImageTextBuilder wrap(int lineHeight, int lineWidth) {
        this.wrapSettings = new TextWrapSettings(lineHeight, lineWidth);
        return this;
    }

    public ImageTextBuilder align(TextAlign textAlign) {
        this.textAlign = textAlign;
        return this;
    }

    public ImageTextBuilder initialX(int x) {
        this.initialX = x;
        return this;
    }

    public ImageTextBuilder initialY(int y) {
        this.initialY = y;
        return this;
    }

    public ImageBuilder finishText() {
        BufferedImage image = this.builder.build();

        Graphics2D g = ImageUtil.setRenderOpts(image.createGraphics());

        Font font = this.font.asFont(this.bold);
        if (font != null) {
            g.setFont(font.deriveFont(this.fontSize));
        } else {
            g.setFont(g.getFont().deriveFont(this.fontSize));
        }

        g.setColor(this.color);

        if (this.wrapSettings != null) {
            List<String> lines = StringUtil.wrap(this.text, g.getFontMetrics(), this.wrapSettings.lineWidth);

            for (int i = 0; i < lines.size(); i++) {
                int yOffset = i * this.wrapSettings.lineHeight;
                ImageUtil.drawText(image, g, lines.get(i), this.initialX, this.initialY + yOffset, this.textAlign);
            }
        } else {
            ImageUtil.drawText(image, g, this.text, this.initialX, this.initialY, this.textAlign);
        }

        g.dispose();
        return this.builder;
    }

    public static ImageTextBuilder create(ImageBuilder builder) {
        return new ImageTextBuilder(builder);
    }

    private static class TextWrapSettings {
        private final int lineHeight;
        private final int lineWidth;

        private TextWrapSettings(int lineHeight, int lineWidth) {
            this.lineHeight = lineHeight;
            this.lineWidth = lineWidth;
        }
    }
}
