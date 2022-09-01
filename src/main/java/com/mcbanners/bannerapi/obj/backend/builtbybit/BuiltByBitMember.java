package com.mcbanners.bannerapi.obj.backend.builtbybit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.builtbybit.BuiltByBitMemberDeserializer;

@JsonDeserialize(using = BuiltByBitMemberDeserializer.class)
public record BuiltByBitMember(
        int memberId,
        String username,
        long joinDate,
        boolean premium,
        boolean supreme,
        boolean ultimate,
        String avatarUrl,
        int postCount,
        int feedbackPositive,
        int feedbackNegative
) {

}
