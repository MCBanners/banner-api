package com.mcbanners.bannerapi.obj.backend.curseforge;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.curseforge.CurseForgeResourceDeserializer;

import java.util.List;

@JsonDeserialize(using = CurseForgeResourceDeserializer.class)
public record CurseForgeResource(
        int id,
        String title,
        String thumbnail,
        int monthlyDownloads,
        int totalDownloads,
        String uploadedAt,
        List<Member> members
) {
    public record Member(int id, String title, String username) {}
}
