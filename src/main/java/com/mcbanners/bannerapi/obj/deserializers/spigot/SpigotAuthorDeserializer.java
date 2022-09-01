package com.mcbanners.bannerapi.obj.deserializers.spigot;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotAuthor;

import java.io.IOException;

public class SpigotAuthorDeserializer extends StdDeserializer<SpigotAuthor> {
    public SpigotAuthorDeserializer() {
        this(null);
    }

    public SpigotAuthorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SpigotAuthor deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);

        return new SpigotAuthor(
                rootNode.get("id").asText(),
                rootNode.get("username").asText(),
                rootNode.get("resource_count").asText(),
                rootNode.get("avatar").asText()
        );
    }
}
