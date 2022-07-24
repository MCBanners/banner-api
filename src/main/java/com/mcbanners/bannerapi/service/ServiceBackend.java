package com.mcbanners.bannerapi.service;

/**
 * Represents a list of available service backends where applicable.
 * Applicable use-cases: querying resources or querying authors
 */
public enum ServiceBackend {
    SPIGOT,
    ORE,
    CURSEFORGE,
    MODRINTH,
    MCMARKET,
    POLYMART
}
