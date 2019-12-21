package com.mcbanners.backend.banner.param.author;

import com.mcbanners.backend.banner.BannerTemplate;
import com.mcbanners.backend.banner.param.TextParameterReader;

import java.util.Map;

public class AuthorParameterReader {
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final TextParameterReader autNameParams;
    private final TextParameterReader resAmountParams;

    public AuthorParameterReader(Map<AuthorParamter, Object> parameters) {
        this.template = (BannerTemplate) parameters.get(AuthorParamter.TEMPLATE);
        this.logoSize = (int) parameters.get(AuthorParamter.LOGO_SIZE);
        this.logoX = (int) parameters.get(AuthorParamter.LOGO_X);
        this.autNameParams = new TextParameterReader("aut_name", parameters);
        this.resAmountParams = new TextParameterReader("res_count", parameters);
    }

    public BannerTemplate getTemplate() {
        return template;
    }

    public int getLogoSize() {
        return logoSize;
    }

    public int getLogoX() {
        return logoX;
    }

    public TextParameterReader getAutNameParams() {
        return autNameParams;
    }

    public TextParameterReader getResAmountParams() {
        return resAmountParams;
    }
}
