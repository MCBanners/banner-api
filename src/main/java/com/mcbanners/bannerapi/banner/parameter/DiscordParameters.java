package com.mcbanners.bannerapi.banner.parameter;

import com.mcbanners.bannerapi.banner.parameter.api.namespace.TextParameterNamespace;

import java.util.Map;

public final class DiscordParameters extends GlobalParameters {
    private final TextParameterNamespace discordName;
    private final TextParameterNamespace id;
    private final TextParameterNamespace status;
    private final TextParameterNamespace activity;
    private final TextParameterNamespace created;

    public DiscordParameters(Map<String, String> rawParameters) {
        super(rawParameters);

        discordName = new TextParameterNamespace("discord_name", rawParameters);
        id = new TextParameterNamespace("id", rawParameters);
        status = new TextParameterNamespace("status", rawParameters);
        activity = new TextParameterNamespace("activity", rawParameters);
        created = new TextParameterNamespace("created", rawParameters);

        // Discord Name defaults
        discordName.getX().defaultValue(104);
        discordName.getY().defaultValue(22);
        discordName.getFontSize().defaultValue(18);
        discordName.getFontBold().defaultValue(true);

        // ID defaults
        id.getX().defaultValue(104);
        id.getY().defaultValue(38);

        // Status defaults
        status.getX().defaultValue(104);
        status.getY().defaultValue(55);

        // Activity defaults
        activity.getX().defaultValue(104);
        activity.getY().defaultValue(72);

        // Created defaults
        created.getX().defaultValue(104);
        created.getY().defaultValue(89);
    }

    public TextParameterNamespace getDiscordName() {
        return discordName;
    }

    public TextParameterNamespace getId() {
        return id;
    }

    public TextParameterNamespace getStatus() {
        return status;
    }

    public TextParameterNamespace getActivity() {
        return activity;
    }

    public TextParameterNamespace getCreated() {
        return created;
    }

    @Override
    public Map<String, Map<String, Object>> defaults() {
        Map<String, Map<String, Object>> output = super.defaults();

        output.put("discord_name", discordName.defaults());
        output.put("id", id.defaults());
        output.put("status", status.defaults());
        output.put("activity", activity.defaults());
        output.put("created", created.defaults());

        return output;
    }
}
