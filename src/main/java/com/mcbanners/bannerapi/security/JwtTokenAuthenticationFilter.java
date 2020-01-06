package com.mcbanners.bannerapi.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtHandler jwtHandler;

    @Autowired
    public JwtTokenAuthenticationFilter(JwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(jwtHandler.getHeader());

        if (header == null || !header.startsWith(jwtHandler.getPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace(jwtHandler.getPrefix(), "");
        try {
            Claims claims = jwtHandler.parse(token);

            UUID id = UUID.fromString(claims.get("id", String.class));
            String username = claims.getSubject();

            if (username != null) {
                AuthedUserInformation user = new AuthedUserInformation(id, username);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        user, null, Collections.emptyList()
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception ignored) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
