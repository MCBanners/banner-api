package com.mcbanners.backend.banner.param.author;

import com.mcbanners.backend.banner.BannerTemplate;

import java.util.Map;

public class AuthorParameterReader {
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final AuthorTextParameterReader autNameParams;
    private final AuthorTextParameterReader resAmountParams;
    private final AuthorTextParameterReader dlCountParams;
    private final AuthorTextParameterReader likesCountParam;
    private final AuthorTextParameterReader revCountParams;

    public AuthorParameterReader(Map<AuthorParameter, Object> parameters) {
        this.template = (BannerTemplate) parameters.get(AuthorParameter.TEMPLATE);
        this.logoSize = (int) parameters.get(AuthorParameter.LOGO_SIZE);
        this.logoX = (int) parameters.get(AuthorParameter.LOGO_X);
        this.autNameParams = new AuthorTextParameterReader("aut_name", parameters);
        this.resAmountParams = new AuthorTextParameterReader("res_count", parameters);
        this.dlCountParams = new AuthorTextParameterReader("dl_count", parameters);
        this.likesCountParam = new AuthorTextParameterReader("likes_count", parameters);
        this.revCountParams = new AuthorTextParameterReader("rev_count", parameters);
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

    public AuthorTextParameterReader getAutNameParams() {
        return autNameParams;
    }

    public AuthorTextParameterReader getResAmountParams() {
        return resAmountParams;
    }

    public AuthorTextParameterReader getDlCountParams() {
        return dlCountParams;
    }

    public AuthorTextParameterReader getLikesCountParam() {
        return likesCountParam;
    }

    public AuthorTextParameterReader getRevCountParams() {
        return revCountParams;
    }
}
