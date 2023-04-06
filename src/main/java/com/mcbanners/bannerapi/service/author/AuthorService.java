package com.mcbanners.bannerapi.service.author;

import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.obj.generic.Resource;
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
     * Get an author by inspecting a resource on the specified service backend
     *
     * @param resource the resource to find the author for
     * @param backend  the service backend to query
     * @return the Author object or null if the author could not be found.
     */
    @Cacheable(unless = "#result == null")
    public Author getAuthor(Resource resource, ServiceBackend backend) {
        // Authors can be identified by an integer or by a String. We don't know which is being given to us
        // sometimes, so the best we can do is check if they have an integer set and use that if so, otherwise
        // falling back to their name.
        final String authorId = resource.authorId() != -1 ? String.valueOf(resource.authorId()) : resource.authorName();
        return getAuthor(authorId, backend);
    }

    /**
     * Get an author by its id on the specified service backend.
     *
     * @param authorId the author ID
     * @param backend  the service backend to query
     * @return the Author object or null if the service backend does not support the operation or the author could not be found.
     */
    @Cacheable(unless = "#result == null")
    public Author getAuthor(String authorId, ServiceBackend backend) {
        // There's no good solution coming to mind for this
        // noinspection DuplicatedCode
        return switch (backend) {
            case SPIGOT -> spigot.handle(authorId);
            case CURSEFORGE -> curseForge.handle(authorId);
            case BUILTBYBIT -> builtByBit.handle(authorId);
            case POLYMART -> polymart.handle(authorId);
            case ORE -> ore.handle(authorId);
            case MODRINTH -> modrinth.handle(authorId);
        };
    }

    /**
     * Get an author by its id and related resource id on the specified service backend (only Polymart for now?)
     *
     * @param authorId   the author ID
     * @param resourceId the resource ID
     * @return the Author object or null if the author could not be found.
     */
    // TODO: preferably want to get rid of this
    @Cacheable(unless = "#result == null")
    public Author getAuthor(int authorId, int resourceId) {
        return polymart.handle(authorId, resourceId);
    }
}
