package com.mcbanners.bannerapi.obj.deserializers.polymart;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartResource;

import java.io.IOException;

public class PolymartResourceDeserializer extends StdDeserializer<PolymartResource> {
    public PolymartResourceDeserializer() {
        this(null);
    }

    public PolymartResourceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PolymartResource deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode root = (JsonNode) p.getCodec().readTree(p).get("response");
        JsonNode resourceNode = root.get("resource");
        JsonNode ownerNode = resourceNode.get("owner");
        JsonNode reviewsNode = resourceNode.get("reviews");

        JsonNode thumbnailURLNode = resourceNode.get("thumbnailURL");
        String thumbnailURL = thumbnailURLNode != null && !thumbnailURLNode.isNull()
                ? thumbnailURLNode.asText() : "";

        return new PolymartResource(
                resourceNode.get("id").asInt(),
                resourceNode.get("title").asText(),
                ownerNode.get("name").asText(),
                ownerNode.get("id").asInt(),
                ownerNode.get("type").asText(),
                ownerNode.get("url").asText(),
                resourceNode.get("price").asDouble(),
                resourceNode.get("currency").asText(),
                resourceNode.get("downloads").asInt(),
                thumbnailURL,
                reviewsNode.get("count").asInt(),
                reviewsNode.get("stars").asInt()
        );
    }
}
