package com.mcbanners.bannerapi.net.upstream;

import com.mcbanners.bannerapi.net.BasicHttpClient;
import me.mrafonso.hangar4j.impl.project.HangarProject;
import me.mrafonso.hangar4j.impl.project.HangarProjects;
import me.mrafonso.hangar4j.impl.user.HangarUser;
import org.springframework.stereotype.Component;

@Component
public class HangarClient extends BasicHttpClient {
    private final me.mrafonso.hangar4j.HangarClient internalClient;

    public HangarClient() {
        super("");
        this.internalClient = new me.mrafonso.hangar4j.HangarClient("MCBanners");
    }

    public HangarProject getProject(String resource) {
        return internalClient.getProject(resource).join();
    }

    public HangarUser getUser(String username) {
        return internalClient.getUser(username).join();
    }

    public HangarProjects getProjectsOfUser(HangarUser user) {
        return internalClient.getUserProjects(user).join();
    }
}
