package com.mcbanners.backend.mcapi.svc;

<<<<<<< Updated upstream
public interface McServerService {
=======
import com.mcbanners.backend.obj.McServer;

public interface McServerService {
    McServer getServer(final String host);
    McServer getServer(final String host, final int port);
>>>>>>> Stashed changes
}
