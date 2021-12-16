package com.mcbanners.bannerapi.obj.backend.curseforge;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurseForgeResourceDownload {
    private String uploadedAt;

    @JsonGetter("uploaded_at")
    public String getUploadedAt() {
        return uploadedAt;
    }

    @JsonSetter("uploaded_at")
    public void setUploadedAt(String uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
