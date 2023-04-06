package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.banner.parameter.DiscordParameters;
import com.mcbanners.bannerapi.obj.backend.discord.DiscordUser;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class DiscordLayout extends Layout<DiscordParameters> {
    private final DiscordUser user;
    private final String username;

    public DiscordLayout(DiscordUser user, Map<String, String> parameters) {
        super(new DiscordParameters(parameters));

        this.user = user;

        String username = parameters().getDiscordName().readDisplay();
        if (username.isEmpty() || username.equalsIgnoreCase("unset")) {
            username = user.name();
        }

        this.username = username;
    }

    @Override
    public List<BasicComponent> build() {
        component(new LogoComponent(
                parameters().getLogo().readX(),
                parameters().getLogo().readSize(),
                user.icon(),
                Sprite.DEFAULT_SERVER_LOGO
        ));

        text(parameters().getDiscordName(), username);

        text(parameters().getId(), "ID: %s", user.id());

        final String updatedStatus = switch (user.status()) {
            case "online" -> "Online";
            case "idle" -> "Idle";
            case "dnd" -> "Do Not Disturb";
            case "offline" -> "Offline";
            default -> "Unknown";
        };

        text(parameters().getStatus(), "Status: %s", updatedStatus);

        if (user.activity() != null) {
            text(parameters().getActivity(), "Activity: %s", user.activity());
        }

        date(parameters().getCreated(), Instant.ofEpochMilli(user.created()), "Created:");

        return components();
    }
}
