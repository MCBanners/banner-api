package engineer.jacob.spigotbanners;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public enum FontFace {
    ACTION_MAN,
    AMADEUS,
    ARCHITECTS_DAUGHTER,
    BREE_SERIF_REGULAR,
    COMFORTAA_REGULAR,
    ERASER_REGULAR,
    FORQUE,
    LATO_REGULAR,
    LOBSTER_TWO,
    PERMANENT_MARKER,
    QARMIC_SANS_ABRIDGED,
    ROBOTO_REGULAR,
    ROCK_SALT,
    TRASH_HAND,
    UBUNTU_TITLE,
    VANILLA,
    COOLVETICA;

    public String getFileName() {
        StringBuilder builder = new StringBuilder();

        String[] split = name().toLowerCase().split("_");
        for (String piece : split) {
            builder.append(piece.substring(0, 1).toUpperCase()).append(piece.substring(1));
        }

        builder.append(".ttf");
        return builder.toString();
    }

    public Font asFont() {
        InputStream stream = getClass().getResourceAsStream("/fonts/" + getFileName());
        try {
            return Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
