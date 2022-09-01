package com.mcbanners.bannerapi.obj.deserializers.ore;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.ore.OreAuthorization;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class OreAuthorizationDeserializer extends StdDeserializer<OreAuthorization> {
    public OreAuthorizationDeserializer() {
        this(null);
    }

    public OreAuthorizationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OreAuthorization deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);

        return new OreAuthorization(
                rootNode.get("session").asText(),
                Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(rootNode.get("expires").asText()))
        );
    }
}
