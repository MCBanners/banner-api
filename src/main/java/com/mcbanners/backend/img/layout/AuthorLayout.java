package com.mcbanners.backend.img.layout;

import com.mcbanners.backend.banner.param.author.AuthorParameterReader;
import com.mcbanners.backend.banner.param.author.AuthorParamter;
import com.mcbanners.backend.obj.Author;

import java.awt.image.BufferedImage;
import java.util.Map;

public class AuthorLayout extends Layout {
    private final Author author;
    private final AuthorParameterReader parameters;

    public AuthorLayout(Author author, Map<AuthorParamter, Object> parameters) {
        this.author = author;

        this.parameters = new AuthorParameterReader(parameters);
    }

    @Override
    public BufferedImage draw() {
        return null;
    }
}
