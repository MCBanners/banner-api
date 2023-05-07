package com.mcbanners.bannerapi.service.resource.backend;

import com.mcbanners.bannerapi.net.upstream.HangarClient;
import com.mcbanners.bannerapi.obj.generic.RatingInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.api.BasicHandler;
import me.mrafonso.hangar4j.impl.project.HangarProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HangarResourceService extends BasicHandler<Resource> {
    private final HangarClient hangar;

    @Autowired
    public HangarResourceService(HangarClient hangar1) {
        this.hangar = hangar1;
    }

    public Resource handle(String resource, String author) {
        final HangarProject project = hangar.getProject(author, resource);
        if (project == null) {
            return null;
        }

        String avatar = hangar.getBase64Image(project.avatarUrl());
        if (avatar == null) {
            avatar = "";
        }

        return new Resource(
                avatar,
                project.name(),
                -1,
                project.namespace().owner(),
                new RatingInformation(project.stats().stars(), 0.0),
                project.stats().downloads(),
                null,
                project.lastUpdated()
        );
    }
}
