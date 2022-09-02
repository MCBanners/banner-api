package com.mcbanners.bannerapi.obj.deserializers.modrinth;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.modrinth.ModrinthUser;

import java.io.IOException;

public class ModrinthUserDeserializer extends StdDeserializer<ModrinthUser> {
    public ModrinthUserDeserializer() {
        this(null);
    }

    public ModrinthUserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ModrinthUser deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);
        if (rootNode.isArray()) {
            rootNode = rootNode.get(0).get("user");
        }

        JsonNode avatarUrlNode = rootNode.get("avatar_url");

        return new ModrinthUser(
                rootNode.get("id").asText(),
                rootNode.get("github_id").asText(),
                rootNode.get("username").asText(),
                rootNode.get("name").asText(),
                avatarUrlNode.isNull() ? "" : avatarUrlNode.asText()
        );
    }
}
