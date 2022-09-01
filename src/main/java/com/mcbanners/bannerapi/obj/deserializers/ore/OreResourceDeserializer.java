package com.mcbanners.bannerapi.obj.deserializers.ore;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.ore.OreResource;

import java.io.IOException;

public class OreResourceDeserializer extends StdDeserializer<OreResource> {
    public OreResourceDeserializer() {
        this(null);
    }

    public OreResourceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OreResource deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);
        JsonNode namespaceNode = rootNode.get("namespace");
        JsonNode statsNode = rootNode.get("stats");

        return new OreResource(
                rootNode.get("plugin_id").asText(),
                rootNode.get("name").asText(),
                namespaceNode.get("owner").asText(),
                namespaceNode.get("slug").asText(),
                statsNode.get("views").asInt(),
                statsNode.get("downloads").asInt(),
                statsNode.get("stars").asInt(),
                rootNode.get("last_updated").asText(),
                rootNode.get("icon_url").asText()
        );
    }
}
