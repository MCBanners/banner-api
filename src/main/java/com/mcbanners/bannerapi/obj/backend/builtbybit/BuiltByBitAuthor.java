package com.mcbanners.bannerapi.obj.backend.builtbybit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.builtbybit.BuiltByBitAuthorDeserializer;

@JsonDeserialize(using = BuiltByBitAuthorDeserializer.class)
public record BuiltByBitAuthor(
        int authorId,
        String username,
        int resourceCount,
        String avatarUrl
) {
}
