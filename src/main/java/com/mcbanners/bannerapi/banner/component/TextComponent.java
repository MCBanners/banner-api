package com.mcbanners.bannerapi.banner.component;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.FontFace;
import com.mcbanners.bannerapi.banner.ImageBuilder;
import com.mcbanners.bannerapi.banner.ImageTextBuilder;
import com.mcbanners.bannerapi.banner.TextAlign;

import java.awt.*;

public class TextComponent extends BasicComponent {
    protected final int fontSize;
    protected final Color fontColor;
    protected final boolean bold;
    protected final TextAlign textAlign;
    protected final FontFace font;
    protected final String content;

    public TextComponent(int x, int y, int fontSize, Color fontColor, boolean bold, TextAlign textAlign, FontFace font, String content) {
        super(x, y);
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.bold = bold;
        this.textAlign = textAlign;
        this.font = font;
        this.content = content;
    }

    protected ImageTextBuilder build(ImageBuilder builder) {
        return builder.text()
                .initialX(this.x)
                .initialY(this.y)
                .fontSize(this.fontSize)
                .color(this.fontColor)
                .bold(this.bold)
                .align(this.textAlign)
                .content(this.content, this.font);
    }

    @Override
    public ImageBuilder draw(ImageBuilder builder, BannerOutputFormat outputType) {
        return build(builder).finishText();
    }
}
