package com.mcbanners.bannerapi.service.resource.backend;

import com.mcbanners.bannerapi.net.error.FurtherProcessingRequiredException;
import com.mcbanners.bannerapi.net.upstream.CurseForgeClient;
import com.mcbanners.bannerapi.obj.backend.curseforge.CurseForgeResource;
import com.mcbanners.bannerapi.obj.generic.RatingInformation;
import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.api.BasicHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CurseForgeResourceService extends BasicHandler<Resource> {
    private final CurseForgeClient client;

    @Autowired
    public CurseForgeResourceService(CurseForgeClient client) {
        this.client = client;
    }

    @Override
    public Resource handle(int resourceId) throws FurtherProcessingRequiredException {
        CurseForgeResource curseForgeResource = loadResource(resourceId);
        if (curseForgeResource == null) {
            return null;
        }

        CurseForgeResource.Member author = curseForgeResource.members().stream()
                .filter(member -> member.title().equalsIgnoreCase("Owner"))
                .findFirst().orElse(null);

        if (author == null) {
            return null;
        }

        String curseForgeResourceIcon = client.getBase64Image(curseForgeResource.thumbnail());

        return new Resource(
                curseForgeResourceIcon,
                curseForgeResource.title(),
                author.id(),
                author.username(),
                new RatingInformation(0, 0.0),
                curseForgeResource.totalDownloads(),
                null,
                curseForgeResource.uploadedAt()
        );
    }

    private CurseForgeResource loadResource(int resourceId) throws FurtherProcessingRequiredException {
        ResponseEntity<CurseForgeResource> resp = client.getResource(resourceId);
        if (resp == null) {
            return null;
        }

        if (resp.getStatusCode().value() == 202) {
            throw new FurtherProcessingRequiredException(
                    "CurseForge is currently processing the requested resource and has asked us to wait while " +
                            "the processing completes. Please try your request again in about 30 seconds. Sorry " +
                            "for the inconvenience.");
        } else {
            return resp.getBody();
        }

    }
}
