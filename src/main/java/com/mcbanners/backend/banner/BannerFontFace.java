package com.mcbanners.backend.banner;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public enum BannerFontFace {
    MONTSERRAT,
    OPEN_SANS,
    POPPINS,
    RALEWAY,
    SOURCE_SANS_PRO;

    public String getFileName(boolean bold) {
        StringBuilder builder = new StringBuilder();

        String[] split = name().toLowerCase().split("_");
        for (String piece : split) {
            builder.append(piece.substring(0, 1).toUpperCase()).append(piece.substring(1));
        }

        builder.append(bold ? "Bold" : "Regular");
        builder.append(".ttf");
        return builder.toString();
    }

    public Font asFont() {
        return asFont(false);
    }

    public Font asFont(boolean bold) {
        InputStream stream = getClass().getResourceAsStream("/fonts/" + getFileName(bold));
        try {
            return Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BannerFontFace fromString(String name) {
        return valueOf(name.toUpperCase());
    }
}
