package com.mcbanners.bannerapi.service.author;

import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.author.backend.BuiltByBitAuthorService;
import com.mcbanners.bannerapi.service.author.backend.CurseForgeAuthorService;
import com.mcbanners.bannerapi.service.author.backend.ModrinthAuthorService;
import com.mcbanners.bannerapi.service.author.backend.OreAuthorService;
import com.mcbanners.bannerapi.service.author.backend.PolymartAuthorService;
import com.mcbanners.bannerapi.service.author.backend.SpigotAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"author"})
public class AuthorService {
    private final SpigotAuthorService spigot;
    private final OreAuthorService ore;
    private final CurseForgeAuthorService curseForge;
    private final ModrinthAuthorService modrinth;
    private final PolymartAuthorService polymart;
    private final BuiltByBitAuthorService builtByBit;

    @Autowired
    public AuthorService(SpigotAuthorService spigot, OreAuthorService ore, CurseForgeAuthorService curseForge, ModrinthAuthorService modrinth, PolymartAuthorService polymart, BuiltByBitAuthorService builtByBit) {
        this.spigot = spigot;
        this.ore = ore;
        this.curseForge = curseForge;
        this.modrinth = modrinth;
        this.polymart = polymart;
        this.builtByBit = builtByBit;
    }

    /**
     * Get an author by its id on the specified service backend.
     *
     * @param authorId the author ID
     * @param backend  the service backend to query
     * @return the Author object or null if the service backend does not support the operation or the author could not be found.
     */
    @Cacheable(unless = "#result == null")
    public Author getAuthor(int authorId, ServiceBackend backend) {
        return switch (backend) {
            case SPIGOT -> spigot.handle(authorId);
            case CURSEFORGE -> curseForge.handle(authorId, null);
            case BUILTBYBIT -> builtByBit.handle(authorId);
            case POLYMART -> polymart.handle(authorId);
            case ORE, MODRINTH -> null;
        };
    }

    /**
     * Get an author by its id and related resource id on the specified service backend (only Polymart for now?)
     *
     * @param authorId the author ID
     * @param resourceId the resource ID
     * @param backend the service backend to query
     * @return the Author object or null if the author could not be found.
     */
    @Cacheable(unless = "#result == null")
    public Author getAuthor(int authorId, int resourceId, ServiceBackend backend) {
        return polymart.handle(authorId, resourceId);
    }

    /**
     * Get an author by its name on the specified service backend.
     *
     * @param authorName the author name
     * @param backend    the service backend to query
     * @return the Author object or null if the service bannerapi does not support the operation or the author could not be found.
     */
    @Cacheable(unless = "#result == null")
    public Author getAuthor(String authorName, ServiceBackend backend) {
        return switch (backend) {
            case ORE -> ore.handle(authorName);
            case CURSEFORGE -> curseForge.handle(0, authorName);
            case MODRINTH -> modrinth.handle(authorName);
            case SPIGOT, POLYMART, BUILTBYBIT -> null;
        };
    }
}
