package com.mcbanners.bannerapi.obj.deserializers.curseforge;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CurseForgeResourceDeserializer extends StdDeserializer<CurseForgeResource> {
    public CurseForgeResourceDeserializer() {
        this(null);
    }

    public CurseForgeResourceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CurseForgeResource deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);
        JsonNode downloadsNode = rootNode.get("downloads");
        JsonNode downloadNode = rootNode.get("download");
        JsonNode membersNode = rootNode.get("members");

        List<CurseForgeResource.Member> members = new ArrayList<>();
        for (JsonNode memberNode : membersNode) {
            members.add(new CurseForgeResource.Member(
                    memberNode.get("id").asInt(),
                    memberNode.get("title").asText(),
                    memberNode.get("username").asText()
            ));
        }

        return new CurseForgeResource(
                rootNode.get("id").asInt(),
                rootNode.get("title").asText(),
                rootNode.get("thumbnail").asText(),
                downloadsNode.get("monthly").asInt(),
                downloadsNode.get("total").asInt(),
                downloadNode.get("uploaded_at").asText(),
                members
        );
    }
}
