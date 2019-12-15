package com.mcbanners.backend.svc;

import com.mcbanners.backend.spiget.obj.SpigetAuthor;
import com.mcbanners.backend.spiget.obj.SpigetResource;

public interface SpigetResourceService {
    SpigetResource getResource(final int resourceId);
}
