package com.mcbanners.bannerapi.obj.backend.curseforge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurseForgeResource {
    private int id;
    private String title;
    private String thumbnail;
    private CurseForgeResourceDownloads downloads;
    private List<CurseForgeResourceMember> members;
    private CurseForgeResourceDownload download;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public CurseForgeResourceDownloads getDownloads() {
        return downloads;
    }

    public void setDownloads(CurseForgeResourceDownloads downloads) {
        this.downloads = downloads;
    }

    public List<CurseForgeResourceMember> getMembers() {
        return members;
    }

    public void setMembers(List<CurseForgeResourceMember> members) {
        this.members = members;
    }

    public CurseForgeResourceDownload getDownload() {
        return download;
    }

    public void setDownload(CurseForgeResourceDownload download) {
        this.download = download;
    }
}
