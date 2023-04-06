package com.mcbanners.bannerapi.obj.backend.ore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.ore.OreAuthorDeserializer;

@JsonDeserialize(using = OreAuthorDeserializer.class)
public record OreAuthor(String name) {
}
