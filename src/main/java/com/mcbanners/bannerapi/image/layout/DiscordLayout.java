package com.mcbanners.bannerapi.image.layout;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.banner.BannerSprite;
import com.mcbanners.bannerapi.banner.BannerTemplate;
import com.mcbanners.bannerapi.banner.param.DiscordParameter;
import com.mcbanners.bannerapi.banner.param.ParameterReader;
import com.mcbanners.bannerapi.banner.param.TextParameterReader;
import com.mcbanners.bannerapi.image.ImageBuilder;
import com.mcbanners.bannerapi.image.component.BasicComponent;
import com.mcbanners.bannerapi.image.component.LogoComponent;
import com.mcbanners.bannerapi.obj.backend.discord.DiscordUser;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DiscordLayout extends Layout {
    private final DiscordUser user;
    private final String username;
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final TextParameterReader<DiscordParameter> discordName;
    private final TextParameterReader<DiscordParameter> id;
    private final TextParameterReader<DiscordParameter> status;
    private final TextParameterReader<DiscordParameter> activity;
    private final TextParameterReader<DiscordParameter> created;

    public DiscordLayout(DiscordUser user, Map<String, String> parameters) {
        this.user = user;

        ParameterReader<DiscordParameter> reader = new ParameterReader<>(DiscordParameter.class, parameters);
        reader.addTextReaders("discord_name", "id", "status", "activity", "created");

        String username = (String) reader.getOrDefault(DiscordParameter.DISCORD_NAME_DISPLAY);
        if (username.isEmpty() || username.equalsIgnoreCase("unset")) {
            username = user.name();
        }

        this.username = username;
        template = reader.getBannerTemplate();
        logoSize = reader.getLogoSize();
        logoX = reader.getLogoX();
        discordName = reader.getTextReader("discord_name");
        id = reader.getTextReader("id");
        status = reader.getTextReader("status");
        activity = reader.getTextReader("activity");
         created = reader.getTextReader("created");
    }

    @Override
    public List<BasicComponent> build() {
        Color textColor = getTextColor(template);

        addComponent(new LogoComponent(logoX, BannerSprite.DEFAULT_SERVER_LOGO, user.icon(), logoSize));
        addComponent(discordName.makeComponent(textColor, username));
        addComponent(id.makeComponent(textColor, String.format("ID: %s", user.id())));

        final String updatedStatus = switch(user.status()) {
            case "online" -> "Online";
            case "idle" -> "Idle";
            case "dnd" -> "Do Not Disturb";
            case "offline" -> "Offline";
            default -> "Unknown";
        };

        addComponent(status.makeComponent(textColor, String.format("Status: %s", updatedStatus)));

        if (user.activity() != null) {
            addComponent(activity.makeComponent(textColor, String.format("Activity: %s", user.activity())));
        }

        final Date date = Date.from(Instant.ofEpochMilli(user.created()));
        final SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        addComponent(created.makeComponent(textColor, String.format("Created: %s", sdf.format(date))));

        return getComponents();
    }

    @Override
    public BufferedImage draw(BannerOutputType outputType) {
        ImageBuilder builder = ImageBuilder.create(template.getImage(), outputType);

        for (BasicComponent component : build()) {
            builder = component.draw(builder, outputType);
        }

        return builder.build();
    }
}
