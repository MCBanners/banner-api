package com.mcbanners.bannerapi.obj.backend.builtbybit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.builtbybit.BuiltByBitResourceDeserializer;

@JsonDeserialize(using = BuiltByBitResourceDeserializer.class)
public record BuiltByBitResource(
        int resourceId,
        int authorId,
        String title,
        double price,
        String currency,
        int purchaseCount,
        int downloadCount,
        int reviewCount,
        double reviewAverage
) {

}
