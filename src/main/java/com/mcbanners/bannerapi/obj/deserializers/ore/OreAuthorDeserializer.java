package com.mcbanners.bannerapi.obj.deserializers.ore;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.ore.OreAuthor;

import java.io.IOException;

public class OreAuthorDeserializer extends StdDeserializer<OreAuthor> {
    public OreAuthorDeserializer() {
        this(null);
    }

    public OreAuthorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OreAuthor deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return new OreAuthor(((JsonNode) p.getCodec().readTree(p)).get("name").asText());
    }
}
