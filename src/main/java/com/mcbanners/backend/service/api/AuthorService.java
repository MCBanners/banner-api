package com.mcbanners.backend.service.api;

import com.mcbanners.backend.obj.generic.Author;
import com.mcbanners.backend.service.ServiceBackend;

public interface AuthorService {
    /**
     * Get an author by its ID on the providing API service (Spiget, Ore, etc)
     *
     * @param authorId the author ID
     * @param backend  the service backend to query
     * @return the generic Author object representing the author
     */
    Author getAuthor(final int authorId, final ServiceBackend backend);

    /**
     * Get an author by its name, if possible, on the providing API service (Spiget, Ore, etc)
     *
     * @param authorName the author name
     * @param backend    the service backend to query
     * @return the generic Author object representing the author
     */
    Author getAuthor(final String authorName, final ServiceBackend backend);
}
