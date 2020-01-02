package com.mcbanners.bannerapi.banner.param.resource;

import com.mcbanners.bannerapi.banner.BannerTemplate;

import java.util.Map;

public class ResourceParameterReader {
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final ResourceTextParameterReader resNameParams;
    private final ResourceTextParameterReader autNameParams;
    private final ResourceTextParameterReader revCountParams;
    private final int starsX;
    private final int starsY;
    private final double starsGap;
    private final ResourceTextParameterReader dlCountParams;
    private final ResourceTextParameterReader priceParams;

    public ResourceParameterReader(Map<ResourceParameter, Object> parameters) {
        this.template = (BannerTemplate) parameters.get(ResourceParameter.TEMPLATE);
        this.logoSize = (int) parameters.get(ResourceParameter.LOGO_SIZE);
        this.logoX = (int) parameters.get(ResourceParameter.LOGO_X);
        this.resNameParams = new ResourceTextParameterReader("res_name", parameters);
        this.autNameParams = new ResourceTextParameterReader("aut_name", parameters);
        this.revCountParams = new ResourceTextParameterReader("rev_count", parameters);
        this.starsX = (int) parameters.get(ResourceParameter.STARS_X);
        this.starsY = (int) parameters.get(ResourceParameter.STARS_Y);
        this.starsGap = (double) parameters.get(ResourceParameter.STARS_GAP);
        this.dlCountParams = new ResourceTextParameterReader("dl_count", parameters);
        this.priceParams = new ResourceTextParameterReader("price", parameters);
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

    public ResourceTextParameterReader getResNameParams() {
        return resNameParams;
    }

    public ResourceTextParameterReader getAutNameParams() {
        return autNameParams;
    }

    public ResourceTextParameterReader getRevCountParams() {
        return revCountParams;
    }

    public int getStarsX() {
        return starsX;
    }

    public int getStarsY() {
        return starsY;
    }

    public double getStarsGap() {
        return starsGap;
    }

    public ResourceTextParameterReader getDlCountParams() {
        return dlCountParams;
    }

    public ResourceTextParameterReader getPriceParams() {
        return priceParams;
    }
}
