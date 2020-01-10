package com.mcbanners.bannerapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class AuthedUserInformationMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtHandler jwtHandler;

    public AuthedUserInformationMethodArgumentResolver(JwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthedUserInformation.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest req = (HttpServletRequest) webRequest.getNativeRequest();

        String header = req.getHeader(jwtHandler.getHeader());
        if (header == null || !header.startsWith(jwtHandler.getPrefix())) {
            return null;
        }

        String token = header.replace(jwtHandler.getPrefix(), "");

        Claims claims = null;
        try {
            claims = jwtHandler.parse(token);
        } catch (Exception ex) {
            return null;
        }

        return claims == null ? null : new AuthedUserInformation(
                UUID.fromString(claims.get("id", String.class)),
                claims.getSubject()
        );
    }
}
