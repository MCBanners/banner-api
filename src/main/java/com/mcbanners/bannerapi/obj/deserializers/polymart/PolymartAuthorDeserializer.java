package com.mcbanners.bannerapi.obj.deserializers.polymart;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.polymart.PolymartAuthor;

import java.io.IOException;

public class PolymartAuthorDeserializer extends StdDeserializer<PolymartAuthor> {
    public PolymartAuthorDeserializer() {
        this(null);
    }

    public PolymartAuthorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PolymartAuthor deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode root = (JsonNode) p.getCodec().readTree(p).get("response");
        JsonNode userNode = root.has("user") ? root.get("user") : root.get("team");
        JsonNode statisticsNode = userNode.get("statistics");

        JsonNode profilePictureURLNode = userNode.get("profilePictureURL");
        String profilePictureURL = profilePictureURLNode != null && !profilePictureURLNode.isNull()
                ? profilePictureURLNode.asText() : "";

        return new PolymartAuthor(
                userNode.get("id").asInt(),
                userNode.get("username").asText(),
                userNode.get("type").asText(),
                profilePictureURL,
                statisticsNode.get("resourceCount").asInt(),
                statisticsNode.get("resourceDownloads").asInt(),
                statisticsNode.get("resourceRatings").asInt(),
                statisticsNode.get("resourceAverageRating").asInt()
        );
    }
}
