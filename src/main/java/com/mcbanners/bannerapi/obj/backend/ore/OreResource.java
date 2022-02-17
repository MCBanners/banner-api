package com.mcbanners.bannerapi.obj.backend.ore;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OreResource {
    private String pluginId;
    private String name;
    private OreResourceNamespace namespace;
    private OreResourceStats stats;
    private String lastUpdated;
    private String iconUrl;

    @JsonGetter("plugin_id")
    public String getPluginId() {
        return pluginId;
    }

    @JsonSetter("plugin_id")
    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OreResourceStats getStats() {
        return stats;
    }

    public void setStats(OreResourceStats stats) {
        this.stats = stats;
    }

    @JsonGetter("last_updated")
    public String getLastUpdated() {
        return lastUpdated;
    }

    @JsonSetter("last_updated")
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @JsonGetter("icon_url")
    public String getIconUrl() {
        return iconUrl;
    }

    @JsonSetter("icon_url")
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public OreResourceNamespace getNamespace() {
        return namespace;
    }

    public void setNamespace(OreResourceNamespace namespace) {
        this.namespace = namespace;
    }
}
