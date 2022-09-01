package com.mcbanners.bannerapi.obj.deserializers.discord;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.discord.DiscordUser;

import java.io.IOException;

public class DiscordUserDeserializer extends StdDeserializer<DiscordUser> {
    public DiscordUserDeserializer() {
        this(null);
    }

    public DiscordUserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DiscordUser deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);

        return new DiscordUser(
                rootNode.get("id").asLong(),
                rootNode.get("name").asText(),
                rootNode.get("activity").asText(),
                rootNode.get("status").asText(),
                rootNode.get("created").asLong(),
                rootNode.get("icon").asText()
        );
    }
}
