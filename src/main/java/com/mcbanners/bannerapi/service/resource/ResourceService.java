package com.mcbanners.bannerapi.service.resource;

import com.mcbanners.bannerapi.net.error.FurtherProcessingRequiredException;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.resource.backend.BuiltByBitResourceService;
import com.mcbanners.bannerapi.service.resource.backend.CurseForgeResourceService;
import com.mcbanners.bannerapi.service.resource.backend.ModrinthResourceService;
import com.mcbanners.bannerapi.service.resource.backend.OreResourceService;
import com.mcbanners.bannerapi.service.resource.backend.PolymartResourceService;
import com.mcbanners.bannerapi.service.resource.backend.SpigotResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"resource"})
public class ResourceService {
    private final SpigotResourceService spigot;
    private final OreResourceService ore;
    private final CurseForgeResourceService curseForge;
    private final ModrinthResourceService modrinth;
    private final BuiltByBitResourceService builtByBit;
    private final PolymartResourceService polymart;

    @Autowired
    public ResourceService(SpigotResourceService spigot, OreResourceService ore, CurseForgeResourceService curseForge, ModrinthResourceService modrinth, BuiltByBitResourceService builtByBit, PolymartResourceService polymart) {
        this.spigot = spigot;
        this.ore = ore;
        this.curseForge = curseForge;
        this.modrinth = modrinth;
        this.builtByBit = builtByBit;
        this.polymart = polymart;
    }

    /**
     * Get a resource by its ID on the specified service backend.
     *
     * @param resourceId the resource ID
     * @param backend    the service backend to query
     * @return the Resource object or null if the service backend does not support the operation or the resource could not be found.
     */
    @Cacheable(unless = "#result == null")
    public Resource getResource(String resourceId, ServiceBackend backend) throws FurtherProcessingRequiredException {
        // There's no good solution coming to mind for this
        // noinspection DuplicatedCode
        return switch (backend) {
            case SPIGOT -> spigot.handle(resourceId);
            case CURSEFORGE -> curseForge.handle(resourceId);
            case BUILTBYBIT -> builtByBit.handle(resourceId);
            case POLYMART -> polymart.handle(resourceId);
            case ORE -> ore.handle(resourceId);
            case MODRINTH -> modrinth.handle(resourceId);
        };
    }
}
