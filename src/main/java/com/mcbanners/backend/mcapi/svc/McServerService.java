package com.mcbanners.backend.mcapi.svc;

import com.mcbanners.backend.obj.McServer;

public interface McServerService {
    McServer getServer(final String host, final int port);
}
