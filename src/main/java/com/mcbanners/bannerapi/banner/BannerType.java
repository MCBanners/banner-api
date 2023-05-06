package com.mcbanners.bannerapi.banner;

import com.mcbanners.bannerapi.service.ServiceBackend;

public enum BannerType {
    SPONGE_AUTHOR(ServiceBackend.ORE),
    SPONGE_RESOURCE(ServiceBackend.ORE),
    SPIGOT_AUTHOR(ServiceBackend.SPIGOT),
    SPIGOT_RESOURCE(ServiceBackend.SPIGOT),
    MINECRAFT_SERVER(null),
    CURSEFORGE_AUTHOR(ServiceBackend.CURSEFORGE),
    CURSEFORGE_RESOURCE(ServiceBackend.CURSEFORGE),
    MODRINTH_AUTHOR(ServiceBackend.MODRINTH),
    MODRINTH_RESOURCE(ServiceBackend.MODRINTH),
    BUILTBYBIT_AUTHOR(ServiceBackend.BUILTBYBIT),
    BUILTBYBIT_RESOURCE(ServiceBackend.BUILTBYBIT),
    BUILTBYBIT_MEMBER(ServiceBackend.BUILTBYBIT),
    POLYMART_AUTHOR(ServiceBackend.POLYMART),
    POLYMART_RESOURCE(ServiceBackend.POLYMART),
    POLYMART_TEAM(ServiceBackend.POLYMART),
    HANGAR_AUTHOR(ServiceBackend.HANGAR),
    HANGAR_RESOURCE(ServiceBackend.HANGAR),
    DISCORD_USER(null);

    private final ServiceBackend relatedServiceBackend;

    BannerType(ServiceBackend relatedServiceBackend) {
        this.relatedServiceBackend = relatedServiceBackend;
    }

    public ServiceBackend getRelatedServiceBackend() {
        return relatedServiceBackend;
    }
}
