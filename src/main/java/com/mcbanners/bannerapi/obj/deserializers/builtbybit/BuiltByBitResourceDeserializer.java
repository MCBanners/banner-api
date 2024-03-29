package com.mcbanners.bannerapi.obj.deserializers.builtbybit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResource;

import java.io.IOException;

public class BuiltByBitResourceDeserializer extends StdDeserializer<BuiltByBitResource> {
    public BuiltByBitResourceDeserializer() {
        this(null);
    }

    public BuiltByBitResourceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BuiltByBitResource deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        final JsonNode rootNode = jp.getCodec().readTree(jp);
        final JsonNode resourceNode = rootNode.has("data") ? rootNode.get("data") : rootNode;

        return new BuiltByBitResource(
                resourceNode.get("resource_id").asInt(),
                resourceNode.get("author_id").asInt(),
                resourceNode.get("title").asText(),
                resourceNode.get("price").asDouble(),
                resourceNode.get("currency").asText(),
                resourceNode.get("purchase_count").asInt(),
                resourceNode.get("download_count").asInt(),
                resourceNode.get("review_count").asInt(),
                resourceNode.get("review_average").asDouble()
        );
    }
}
