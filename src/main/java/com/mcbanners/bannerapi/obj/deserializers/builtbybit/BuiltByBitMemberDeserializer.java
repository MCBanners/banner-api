package com.mcbanners.bannerapi.obj.deserializers.builtbybit;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitMember;

import java.io.IOException;

public class BuiltByBitMemberDeserializer extends StdDeserializer<BuiltByBitMember> {
    public BuiltByBitMemberDeserializer() {
        this(null);
    }

    public BuiltByBitMemberDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BuiltByBitMember deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        final JsonNode rootNode = jp.getCodec().readTree(jp);
        final JsonNode memberNode = rootNode.get("data");

        return new BuiltByBitMember(
                rootNode.get("result").asText(),
                memberNode.get("member_id").asInt(),
                memberNode.get("username").asText(),
                memberNode.get("join_date").asLong(),
                memberNode.get("premium").asBoolean(),
                memberNode.get("supreme").asBoolean(),
                memberNode.get("ultimate").asBoolean(),
                memberNode.get("avatar_url").asText(),
                memberNode.get("post_count").asInt(),
                memberNode.get("feedback_positive").asInt(),
                memberNode.get("feedback_negative").asInt()
        );
    }
}
