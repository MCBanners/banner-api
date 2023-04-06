package com.mcbanners.bannerapi.obj.deserializers.builtbybit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResource;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitResourceBasic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuiltByBitResourceBasicDeserializer extends StdDeserializer<BuiltByBitResourceBasic> {
    public BuiltByBitResourceBasicDeserializer() {
        this(null);
    }

    public BuiltByBitResourceBasicDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BuiltByBitResourceBasic deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        final ObjectCodec codec = jp.getCodec();
        final JsonNode rootNode = codec.readTree(jp);

        final List<BuiltByBitResource> resources = new ArrayList<>();
        for (JsonNode node : rootNode.get("data")) {
            resources.add(codec.treeToValue(node, BuiltByBitResource.class));
        }

        return new BuiltByBitResourceBasic(resources);
    }
}
