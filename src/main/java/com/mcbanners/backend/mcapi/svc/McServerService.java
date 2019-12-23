package com.mcbanners.backend.mcapi.svc;

import com.mcbanners.backend.obj.Server;

public interface McServerService {
    Server getServer(final String host);
}
