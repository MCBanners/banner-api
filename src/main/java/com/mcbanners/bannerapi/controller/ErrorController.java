package com.mcbanners.bannerapi.controller;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/error")
public class ErrorController extends AbstractErrorController {
    public ErrorController(ErrorAttributes attributes) {
        super(attributes, Collections.emptyList());
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest req) {
        return new ResponseEntity<>(
                getErrorAttributes(req, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE)),
                getStatus(req)
        );
    }
}
