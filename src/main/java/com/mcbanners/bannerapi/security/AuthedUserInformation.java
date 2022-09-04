package com.mcbanners.bannerapi.security;

import java.util.UUID;

public record AuthedUserInformation(UUID id, String username) {
}
