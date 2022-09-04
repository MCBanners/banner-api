package com.mcbanners.bannerapi.net.upstream;

import com.mcbanners.bannerapi.obj.backend.discord.DiscordUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "discord-service")
public interface DiscordClient {

    @GetMapping("/user")
    DiscordUser getDiscordUser(@RequestParam String id);
}
