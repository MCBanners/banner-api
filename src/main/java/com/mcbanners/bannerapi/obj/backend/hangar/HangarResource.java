package com.mcbanners.bannerapi.obj.backend.hangar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HangarResource {
    private String name;
    private HangarNamespace namespace;
    private HangarStats stats;
    private String category;
    private String visibility;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HangarNamespace getNamespace() {
        return namespace;
    }

    public void setNamespace(HangarNamespace namespace) {
        this.namespace = namespace;
    }

    public HangarStats getStats() {
        return stats;
    }

    public void setStats(HangarStats stats) {
        this.stats = stats;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
