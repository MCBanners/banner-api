package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.obj.backend.mcapi.MinecraftServer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mc-service")
public interface McAPIClient {
    @GetMapping(value = "/server")
    MinecraftServer getMinecraftServer(@RequestParam("host") String host, @RequestParam("port") int port);
}
