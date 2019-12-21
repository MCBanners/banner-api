package com.mcbanners.backend.obj.spiget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpigetAuthor {
    private int id;
    private String name;
    private SpigetIcon icon;
    private SpigetResource[] resources;

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

    public SpigetResource[] getResources() {
        return resources;
    }

    public void setResources(SpigetResource[] resources) {
        this.resources = resources;
    }
}
