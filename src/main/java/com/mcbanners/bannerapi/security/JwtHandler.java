package com.mcbanners.bannerapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtHandler {
    private JwtParser parser;

    @Value("${security.jwt.uri:/user/**}")
    private String uri;
    @Value("${security.jwt.header:Authorization}")
    private String header;
    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;
    @Value("${security.jwt.secret:secret}")
    private String secret;

    public Claims parse(String token) throws JwtException {
        return parser.parseSignedClaims(token).getPayload();
    }

    @PostConstruct
    public void postConstruct() {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.parser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String getUri() {
        return uri;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }
}
