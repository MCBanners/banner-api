package com.mcbanners.bannerapi.obj.deserializers.builtbybit;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitAuthor;

import java.io.IOException;

public class BuiltByBitAuthorDeserializer extends StdDeserializer<BuiltByBitAuthor> {
    public BuiltByBitAuthorDeserializer() {
        this(null);
    }

    public BuiltByBitAuthorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BuiltByBitAuthor deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        final JsonNode rootNode = jp.getCodec().readTree(jp);
        final JsonNode authorNode = rootNode.get("data");

        return new BuiltByBitAuthor(
                authorNode.get("member_id").asInt(),
                authorNode.get("username").asText(),
                authorNode.get("resource_count").asInt(),
                authorNode.get("avatar_url").asText()
        );
    }
}
