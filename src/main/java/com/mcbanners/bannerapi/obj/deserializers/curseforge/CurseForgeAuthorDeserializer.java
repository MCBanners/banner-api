package com.mcbanners.bannerapi.obj.deserializers.curseforge;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeAuthor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CurseForgeAuthorDeserializer extends StdDeserializer<CurseForgeAuthor> {
    public CurseForgeAuthorDeserializer() {
        this(null);
    }

    public CurseForgeAuthorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CurseForgeAuthor deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);
        JsonNode projectsNode = rootNode.get("projects");

        List<CurseForgeAuthor.Project> projects = new ArrayList<>();
        for (JsonNode projectNode : projectsNode) {
            projects.add(new CurseForgeAuthor.Project(
                    projectNode.get("id").asInt(),
                    projectNode.get("name").asText()
            ));
        }

        return new CurseForgeAuthor(
                rootNode.get("id").asInt(),
                rootNode.get("username").asText(),
                projects
        );
    }
}
