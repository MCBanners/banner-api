package com.mcbanners.backend.svc;

import com.mcbanners.backend.spiget.obj.SpigetAuthor;

public interface SpigetAuthorService {
    SpigetAuthor getAuthor(final int authorId);
}
