package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.image.BannerImageWriter;
import com.mcbanners.bannerapi.image.layout.ServerLayout;
import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;
import com.mcbanners.bannerapi.service.api.MinecraftServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        MinecraftServer server = this.servers.getServer(host, port);
        return new ResponseEntity<>(Collections.singletonMap("valid", server != null), HttpStatus.OK);
    }

    @GetMapping(value = "/{host}/{port}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable String host, @PathVariable int port, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        MinecraftServer server = this.servers.getServer(host, port);
        if (server == null) {
            return null;
        }

        return BannerImageWriter.write(new ServerLayout(server, raw).draw(outputType), outputType);
    }
}
