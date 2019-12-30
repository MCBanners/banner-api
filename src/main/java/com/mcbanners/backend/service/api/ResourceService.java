package com.mcbanners.backend.service.api;

import com.mcbanners.backend.obj.generic.Resource;
import com.mcbanners.backend.service.ServiceBackend;

public interface ResourceService {
    /**
     * Get a resource by its ID on the providing API service (Spiget, Ore, etc)
     *
     * @param resourceId the resource ID
     * @param backend    the service backend to query
     * @return the generic Resource object representing the resource
     */
    Resource getResource(final int resourceId, final ServiceBackend backend);
}
