package com.mcbanners.bannerapi.security;

import java.util.UUID;

public class AuthedUserInformation {
    private final UUID id;
    private final String username;

    public AuthedUserInformation(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
