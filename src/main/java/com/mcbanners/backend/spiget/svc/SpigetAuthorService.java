package com.mcbanners.backend.spiget.svc;

import com.mcbanners.backend.obj.Author;

public interface SpigetAuthorService {
    Author getAuthor(final int authorId);
}
