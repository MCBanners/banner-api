package com.mcbanners.bannerapi.obj.deserializers.mcapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.discord.DiscordUser;
import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;

import java.io.IOException;

public class MinecraftServerDeserializer extends StdDeserializer<MinecraftServer> {
    public MinecraftServerDeserializer() {
        this(null);
    }

    public MinecraftServerDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MinecraftServer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);
        JsonNode playersNode = rootNode.get("players");
        JsonNode motdNode = rootNode.get("motd");

        return new MinecraftServer(
                rootNode.get("host").asText(),
                rootNode.get("port").asInt(),
                rootNode.get("version").asText(),
                playersNode.get("online").asInt(),
                playersNode.get("max").asInt(),
                motdNode.get("raw").asText(),
                motdNode.get("formatted").asText(),
                rootNode.get("icon").asText()
        );
    }
}
