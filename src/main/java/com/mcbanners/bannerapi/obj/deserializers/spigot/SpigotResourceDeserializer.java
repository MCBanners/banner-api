package com.mcbanners.bannerapi.obj.deserializers.spigot;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.spigot.SpigotResource;

import java.io.IOException;

public class SpigotResourceDeserializer extends StdDeserializer<SpigotResource> {
    public SpigotResourceDeserializer() {
        this(null);
    }

    public SpigotResourceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SpigotResource deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);
        JsonNode premiumNode = rootNode.get("premium");
        JsonNode statsNode = rootNode.get("stats");
        JsonNode reviewsNode = statsNode.get("reviews");
        JsonNode authorNode = rootNode.get("author");

        return new SpigotResource(
                rootNode.get("id").asText(),
                rootNode.get("title").asText(),
                rootNode.get("tag").asText(),
                rootNode.get("current_version").asText(),
                rootNode.get("icon_link").asText(),
                premiumNode.get("price").asText(),
                premiumNode.get("currency").asText(),
                statsNode.get("downloads").asText(),
                statsNode.get("updates").asText(),
                reviewsNode.get("unique").asText(),
                reviewsNode.get("total").asText(),
                statsNode.get("rating").asText(),
                authorNode.get("id").asText(),
                authorNode.get("username").asText()
        );
    }
}
