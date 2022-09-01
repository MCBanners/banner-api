package com.mcbanners.bannerapi.obj.backend.ore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.ore.OreAuthorizationDeserializer;

import java.time.Instant;

@JsonDeserialize(using = OreAuthorizationDeserializer.class)
public record OreAuthorization(String session, Instant expires) {}