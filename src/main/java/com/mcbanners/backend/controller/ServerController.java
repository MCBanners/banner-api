package com.mcbanners.backend.controller;

import com.mcbanners.backend.mcapi.svc.McServerService;
import com.mcbanners.backend.obj.McServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("server")
public class ServerController {
    private final McServerService servers;

    @Autowired
    public ServerController(McServerService servers) {
        this.servers = servers;
    }

    @GetMapping(value = "/{host}/{port}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable String host, @PathVariable(required = false) Integer port) {
        McServer server;
        if (port == null) {
            server = this.servers.getServer(host, 25565);
        }
        else {
            server = this.servers.getServer(host, port);
        }
        return new ResponseEntity<>(Collections.singletonMap("valid", server != null), HttpStatus.OK);
    }
}
