package com.mcbanners.bannerapi.obj.deserializers.modrinth;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthResource;

import java.io.IOException;

public class ModrinthResourceDeserializer extends StdDeserializer<ModrinthResource> {
    public ModrinthResourceDeserializer() {
        this(null);
    }

    public ModrinthResourceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ModrinthResource deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);

        return new ModrinthResource(
                rootNode.get("slug").asText(),
                rootNode.get("team").asText(),
                rootNode.get("title").asText(),
                rootNode.get("updated").asText(),
                rootNode.get("downloads").asInt(),
                rootNode.get("followers").asInt(),
                rootNode.get("icon_url").asText()
        );
    }
}
