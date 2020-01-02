package com.mcbanners.bannerapi.image.component;

import com.mcbanners.bannerapi.banner.BannerFontFace;
import com.mcbanners.bannerapi.banner.BannerTextAlign;
import com.mcbanners.bannerapi.image.ImageBuilder;

import java.awt.*;

public class TextComponent extends BasicComponent {
    private final int fontSize;
    private final Color fontColor;
    private final boolean bold;
    private final BannerTextAlign textAlign;
    private final BannerFontFace font;
    private final String content;

    public TextComponent(int x, int y, int fontSize, Color fontColor, boolean bold, BannerTextAlign textAlign, BannerFontFace font, String content) {
        super(x, y);
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.bold = bold;
        this.textAlign = textAlign;
        this.font = font;
        this.content = content;
    }

    @Override
    public ImageBuilder draw(ImageBuilder builder) {
        return builder.text()
                .initialX(this.x)
                .initialY(this.y)
                .fontSize(this.fontSize)
                .color(this.fontColor)
                .bold(this.bold)
                .align(this.textAlign)
                .content(this.content, this.font)
                .finishText();
    }
}
