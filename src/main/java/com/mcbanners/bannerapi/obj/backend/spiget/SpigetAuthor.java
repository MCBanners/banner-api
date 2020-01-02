package com.mcbanners.bannerapi.obj.backend.spiget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpigetAuthor {
    private int id;
    private String name;
    private SpigetIcon icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpigetIcon getIcon() {
        return icon;
    }

    public void setIcon(SpigetIcon icon) {
        this.icon = icon;
    }
}
