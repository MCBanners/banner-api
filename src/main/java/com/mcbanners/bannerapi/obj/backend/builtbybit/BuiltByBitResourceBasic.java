package com.mcbanners.bannerapi.obj.backend.builtbybit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.builtbybit.BuiltByBitResourceBasicDeserializer;

import java.util.List;

@JsonDeserialize(using = BuiltByBitResourceBasicDeserializer.class)
public record BuiltByBitResourceBasic(List<BuiltByBitResource> resources) {}