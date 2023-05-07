package com.mcbanners.bannerapi.service.author.backend;

import com.mcbanners.bannerapi.net.upstream.HangarClient;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.service.api.BasicHandler;
import me.mrafonso.hangar4j.impl.project.HangarProject;
import me.mrafonso.hangar4j.impl.project.HangarProjects;
import me.mrafonso.hangar4j.impl.user.HangarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HangarAuthorService extends BasicHandler<Author> {
    private final HangarClient hangar;

    @Autowired
    public HangarAuthorService(HangarClient hangar) {
        this.hangar = hangar;
    }

    @Override
    public Author handle(String username) {
        final HangarUser user = hangar.getUser(username);
        if (user == null) {
            return null;
        }

        final HangarProjects projects = hangar.getProjectsOfUser(user);
        if (projects == null || projects.result().isEmpty()) {
            return null;
        }

        int totalDownloads = 0;
        int totalStars = 0;
        int totalWatchers = 0;

        for (final HangarProject project : projects.result()) {
            totalDownloads += project.stats().downloads();
            totalStars += project.stats().stars();
            totalWatchers = project.stats().watchers();
        }

        String avatar = hangar.getBase64Image(user.avatarUrl());
        if (avatar == null) {
            avatar = "";
        }

        return new Author(
                user.name(),
                user.projectCount(),
                avatar,
                totalDownloads,
                totalStars,
                totalWatchers
        );
    }
}
