package com.mcbanners.backend.controller;

import com.mcbanners.backend.banner.param.server.ServerParameter;
import com.mcbanners.backend.img.layout.ServerLayout;
import com.mcbanners.backend.mcapi.svc.McServerService;
import com.mcbanners.backend.obj.McServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    @GetMapping(value = "/{host}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable String host) {
        McServer server = this.servers.getServer(host);
        return new ResponseEntity<>(Collections.singletonMap("valid", server != null), HttpStatus.OK);
    }

    @GetMapping(value = "/{host}/banner.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable String host, @RequestParam Map<String, String> raw) {
        McServer server = this.servers.getServer(host);
        if (server == null) {
            return null;
        }

        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedImage banner = new ServerLayout(server, ServerParameter.parse(raw)).draw();
            ImageIO.write(banner, "png", bos);
            bos.flush();
            return new ResponseEntity<>(bos.toByteArray(), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(new byte[]{}, HttpStatus.NO_CONTENT);
        }
    }
}
