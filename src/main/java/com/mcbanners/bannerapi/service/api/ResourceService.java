package com.mcbanners.bannerapi.service.api;

import com.mcbanners.bannerapi.obj.generic.Resource;
import com.mcbanners.bannerapi.service.ServiceBackend;

public interface ResourceService {
    /**
     * Get a resource by its ID on the providing API service (Spiget, Ore, etc)
     *
     * @param resourceId the resource ID
     * @param backend    the service bannerapi to query
     * @return the generic Resource object representing the resource
     */
    Resource getResource(final int resourceId, final ServiceBackend backend);

    /**
     * Get a resource by its ID as a string on the providing API service
     *
     * @param pluginId the id of the plugin
     * @param backend  the backend to query
     * @return the generic Resource object that represents the resource
     */
    Resource getResource(final String pluginId, final ServiceBackend backend);
}
