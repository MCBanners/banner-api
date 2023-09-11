package com.mcbanners.bannerapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcbanners.bannerapi.banner.BannerImageWriter;
import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.BannerType;
import com.mcbanners.bannerapi.banner.layout.AuthorLayout;
import com.mcbanners.bannerapi.banner.layout.Layout;
import com.mcbanners.bannerapi.banner.layout.MemberLayout;
import com.mcbanners.bannerapi.banner.layout.ResourceLayout;
import com.mcbanners.bannerapi.banner.layout.ServerLayout;
import com.mcbanners.bannerapi.banner.layout.TeamLayout;
import com.mcbanners.bannerapi.banner.parameter.AuthorParameters;
import com.mcbanners.bannerapi.banner.parameter.MemberParameters;
import com.mcbanners.bannerapi.banner.parameter.ResourceParameters;
import com.mcbanners.bannerapi.banner.parameter.ServerParameters;
import com.mcbanners.bannerapi.banner.parameter.TeamParameters;
import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.obj.generic.Member;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.obj.generic.Team;
import com.mcbanners.bannerapi.obj.http.BannerSaveBody;
import com.mcbanners.bannerapi.persistence.SavedBanner;
import com.mcbanners.bannerapi.persistence.SavedBannerRepository;
import com.mcbanners.bannerapi.security.AuthedUserInformation;
import com.mcbanners.bannerapi.service.MemberService;
import com.mcbanners.bannerapi.service.MinecraftServerService;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.TeamService;
import com.mcbanners.bannerapi.service.author.AuthorService;
import com.mcbanners.bannerapi.service.resource.ResourceService;
import com.mcbanners.bannerapi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("saved")
public class SavedController {
    private final ResourceService resources;
    private final AuthorService authors;
    private final MinecraftServerService servers;
    private final MemberService members;
    private final TeamService teams;
    private final SavedBannerRepository repository;
    private final ObjectMapper mapper;

    @Autowired
    public SavedController(ResourceService resources, SavedBannerRepository repository, AuthorService authors, MinecraftServerService servers, MemberService members, TeamService teams, ObjectMapper mapper) {
        this.resources = resources;
        this.repository = repository;
        this.authors = authors;
        this.servers = servers;
        this.members = members;
        this.teams = teams;
        this.mapper = mapper;
    }

    @PostMapping(value = "save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SavedBanner> saveBanner(AuthedUserInformation user, @RequestBody BannerSaveBody body) throws JsonProcessingException {
        SavedBanner banner = new SavedBanner();

        banner.setBannerType(body.type());

        if (user != null) {
            banner.setOwner(user.id());
        }

        banner.setMnemonic(StringUtil.generateMnemonic());

        if (body.metadata() == null || body.metadata().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Did not receive required banner metadata; cannot proceed.");
        }

        banner.setMetadata(mapper.writeValueAsString(body.metadata()));

        if (body.settings() != null) {
            banner.setSettings(mapper.writeValueAsString(body.settings()));
        } else {
            // This really shouldn't happen - it means the caller did not even provide the settings map (even if it's empty.)
            // TODO: report this scenario to Sentry
            banner.setSettings("{}");
        }

        banner = repository.save(banner);

        if (repository.existsById(banner.getId())) {
            return new ResponseEntity<>(banner, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to save banner");
        }
    }

    @GetMapping(value = "/{mnemonic}.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> recall(@PathVariable String mnemonic, @PathVariable BannerOutputFormat outputType) throws JsonProcessingException {
        final SavedBanner banner = repository.findByMnemonic(mnemonic);
        if (banner == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Banner not found");
        }

        final BannerType type = banner.getBannerType();
        final Map<String, String> metadata = mapper.readValue(banner.getMetadata(), new TypeReference<>() {
        });
        final Map<String, String> settings = mapper.readValue(banner.getSettings(), new TypeReference<>() {
        });

        final Layout<?> layout = switch (banner.getBannerType()) {
            case SPIGOT_AUTHOR, SPONGE_AUTHOR, CURSEFORGE_AUTHOR, MODRINTH_AUTHOR, BUILTBYBIT_AUTHOR, POLYMART_AUTHOR, HANGAR_AUTHOR ->
                    getAuthorLayout(type, metadata, settings);
            case SPIGOT_RESOURCE, SPONGE_RESOURCE, CURSEFORGE_RESOURCE, MODRINTH_RESOURCE, BUILTBYBIT_RESOURCE, POLYMART_RESOURCE, HANGAR_RESOURCE ->
                    getResourceLayout(type, metadata, settings);
            case MINECRAFT_SERVER -> getMinecraftServerLayout(metadata, settings);
            case BUILTBYBIT_MEMBER -> getBuiltByBitMemberLayout(metadata, settings);
            case POLYMART_TEAM -> getPolymartTeamLayout(metadata, settings);
            case DISCORD_USER -> throw new RuntimeException("Discord is not yet implemented.");
        };

        return BannerImageWriter.write(layout, outputType);
    }

    private Layout<AuthorParameters> getAuthorLayout(BannerType type, Map<String, String> metadata, Map<String, String> settings) {
        ServiceBackend backend = type.getRelatedServiceBackend();

        Author author = authors.getAuthor(metadata.get("author_id"), backend);
        if (author == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The stored author could not be found!");
        }

        return new AuthorLayout(author, backend, settings);
    }

    private Layout<ResourceParameters> getResourceLayout(BannerType type, Map<String, String> metadata, Map<String, String> settings) {
        ServiceBackend backend = type.getRelatedServiceBackend();

        Resource resource = resources.getResource(metadata.get("resource_id"), backend);

        // TODO: get rid of this
        Author author;
        if (backend == ServiceBackend.POLYMART) {
            author = authors.getAuthor(resource.authorId(), Integer.parseInt(metadata.get("resource_id")));
        } else {
            author = authors.getAuthor(resource, backend);
        }

        if (author == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Either the stored resource or author could not be found!");
        }

        return new ResourceLayout(resource, author, backend, settings);
    }

    private Layout<ServerParameters> getMinecraftServerLayout(Map<String, String> metadata, Map<String, String> settings) {
        String host = metadata.get("server_host");
        if (host == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Host not specified in banner settings");
        }

        int port = 25565;
        try {
            port = Integer.parseInt(metadata.get("server_port"));
        } catch (NumberFormatException ignored) {
        }

        MinecraftServer server = servers.getServer(host, port);
        if (server == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Server not found (or not pingable)!");
        }

        return new ServerLayout(server, settings);
    }

    private Layout<MemberParameters> getBuiltByBitMemberLayout(Map<String, String> metadata, Map<String, String> settings) {
        ServiceBackend backend = ServiceBackend.BUILTBYBIT;

        Member member = members.getMember(Integer.parseInt(metadata.get("member_id")), backend);
        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The member author could not be found!");
        }

        return new MemberLayout(member, settings);
    }

    private Layout<TeamParameters> getPolymartTeamLayout(Map<String, String> metadata, Map<String, String> settings) {
        Team team = teams.getTeam(Integer.parseInt(metadata.get("team_id")), ServiceBackend.POLYMART);
        if (team == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The stored team could not be found!");
        }

        return new TeamLayout(team, settings);
    }
}
