package com.mcbanners.bannerapi.obj.backend.mcapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MinecraftPlayerStatistics {
    private int online;
    private int max;

    public int getOnline() {
        return online;
    }

    public int getMax() {
        return max;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
