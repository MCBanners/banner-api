package com.mcbanners.bannerapi.obj.backend.curseforge;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mcbanners.bannerapi.obj.deserializers.curseforge.CurseForgeAuthorDeserializer;

import java.util.List;

@JsonDeserialize(using = CurseForgeAuthorDeserializer.class)
public record CurseForgeAuthor(
        int id,
        String username,
        List<Project> projects
) {
    public record Project(int id, String name) {
    }
}
