package com.mcbanners.bannerapi.service.author.backend;

import com.mcbanners.bannerapi.net.upstream.DummyClient;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.service.api.BasicHandler;
import me.mrafonso.hangar4j.HangarClient;
import me.mrafonso.hangar4j.impl.project.HangarProject;
import me.mrafonso.hangar4j.impl.project.HangarProjects;
import me.mrafonso.hangar4j.impl.user.HangarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HangarAuthorService extends BasicHandler<Author> {
    private final HangarClient hangarClient;
    private final DummyClient dummyClient;

    @Autowired
    public HangarAuthorService(DummyClient dummyClient) {
        this.dummyClient = dummyClient;
        this.hangarClient = new HangarClient("MCBanners");
    }

    @Override
    public Author handle(String username) {
        final HangarUser user = hangarClient.getUser(username).join();
        if (user == null) {
            return null;
        }

        final HangarProjects projects = hangarClient.getProjectsOfUser(user).join();
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

        String avatar = dummyClient.getBase64Image(user.avatarUrl());
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
