package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.BannerImageWriter;
import com.mcbanners.bannerapi.banner.layout.ServerLayout;
import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;
import com.mcbanners.bannerapi.service.MinecraftServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("server")
public class ServerController {
    private final MinecraftServerService servers;

    @Autowired
    public ServerController(MinecraftServerService servers) {
        this.servers = servers;
    }

    @GetMapping(value = "/{host}/{port}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable String host, @PathVariable int port) {
        return new ResponseEntity<>(Collections.singletonMap("valid", this.servers.getServer(host, port) != null), HttpStatus.OK);
    }

    @GetMapping(value = "/{host}/{port}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable String host, @PathVariable int port, @PathVariable BannerOutputFormat outputType, @RequestParam Map<String, String> raw) {
        final MinecraftServer server = this.servers.getServer(host, port);
        if (server == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return BannerImageWriter.write(new ServerLayout(server, raw), outputType);
    }
}
