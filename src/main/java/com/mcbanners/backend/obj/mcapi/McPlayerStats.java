package com.mcbanners.backend.obj.mcapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class McPlayerStats {
    private final int online;
    private final int max;

    public McPlayerStats(int online, int max) {
        this.online = online;
        this.max = max;
    }

    public int getOnline() {
        return online;
    }

    public int getMax() {
        return max;
    }
}
