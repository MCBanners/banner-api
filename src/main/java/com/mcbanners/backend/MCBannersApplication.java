package com.mcbanners.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MCBannersApplication {
    public static void main(String[] args) {
        SpringApplication.run(MCBannersApplication.class, args);
    }
}
