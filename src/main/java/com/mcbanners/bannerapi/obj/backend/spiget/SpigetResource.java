package com.mcbanners.bannerapi.obj.backend.spiget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpigetResource {
    private SpigetIcon icon;

    public SpigetIcon getIcon() {
        return icon;
    }

    public void setIcon(SpigetIcon icon) {
        this.icon = icon;
    }
}
