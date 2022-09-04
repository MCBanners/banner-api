package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.ImageBuilder;
import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.banner.parameter.DiscordParameters;
import com.mcbanners.bannerapi.obj.backend.discord.DiscordUser;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DiscordLayout extends Layout {
    private final DiscordUser user;
    private final DiscordParameters discordParameters;
    private final String username;

    public DiscordLayout(DiscordUser user, Map<String, String> parameters) {
        this.user = user;
        this.discordParameters = new DiscordParameters(parameters);

        String username = discordParameters.getDiscordName().readDisplay();
        if (username.isEmpty() || username.equalsIgnoreCase("unset")) {
            username = user.name();
        }

        this.username = username;
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(discordParameters.getBackground().readTemplate());

        addComponent(new LogoComponent(
                discordParameters.getLogo().readX(),
                discordParameters.getLogo().readSize(),
                user.icon(),
                Sprite.DEFAULT_SERVER_LOGO
        ));

        addComponent(discordParameters.getDiscordName().asTextComponent(textColor, username));

        addComponent(discordParameters.getId().asTextComponent(textColor, String.format("ID: %s", user.id())));

        final String updatedStatus = switch (user.status()) {
            case "online" -> "Online";
            case "idle" -> "Idle";
            case "dnd" -> "Do Not Disturb";
            case "offline" -> "Offline";
            default -> "Unknown";
        };

        addComponent(discordParameters.getStatus().asTextComponent(textColor, String.format("Status: %s", updatedStatus)));

        if (user.activity() != null) {
            addComponent(discordParameters.getActivity().asTextComponent(textColor, String.format("Activity: %s", user.activity())));
        }

        final Date date = Date.from(Instant.ofEpochMilli(user.created()));
        final SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        addComponent(discordParameters.getCreated().asTextComponent(textColor, String.format("Created: %s", sdf.format(date))));

        return getComponents();
    }

    @Override
    public BufferedImage draw(BannerOutputFormat outputType) {
        ImageBuilder builder = ImageBuilder.create(discordParameters.getBackground().readTemplate().getImage(), outputType);

        for (BasicComponent component : build()) {
            builder = component.draw(builder, outputType);
        }

        return builder.build();
    }
}
