package com.mcbanners.bannerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BannerApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BannerApiApplication.class, args);
    }
}
