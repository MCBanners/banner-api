package com.mcbanners.backend.banner.param.res;

import com.mcbanners.backend.banner.BannerTemplate;
import com.mcbanners.backend.banner.param.ResTextParameterReader;
import com.mcbanners.backend.banner.param.TextParameterReader;

import java.util.Map;

public class ResourceParameterReader  {
    private final BannerTemplate template;
    private final int logoSize;
    private final int logoX;
    private final TextParameterReader resNameParams;
    private final TextParameterReader autNameParams;
    private final TextParameterReader revCountParams;
    private final int starsX;
    private final int starsY;
    private final double starsGap;
    private final TextParameterReader dlCountParams;
    private final TextParameterReader priceParams;

    public ResourceParameterReader(Map<ResourceParameter, Object> parameters) {
        this.template = (BannerTemplate) parameters.get(ResourceParameter.TEMPLATE);
        this.logoSize = (int) parameters.get(ResourceParameter.LOGO_SIZE);
        this.logoX = (int) parameters.get(ResourceParameter.LOGO_X);
        this.resNameParams = new ResTextParameterReader("res_name", parameters);
        this.autNameParams = new ResTextParameterReader("aut_name", parameters);
        this.revCountParams = new ResTextParameterReader("rev_count", parameters);
        this.starsX = (int) parameters.get(ResourceParameter.STARS_X);
        this.starsY = (int) parameters.get(ResourceParameter.STARS_Y);
        this.starsGap = (double) parameters.get(ResourceParameter.STARS_GAP);
        this.dlCountParams = new ResTextParameterReader("dl_count", parameters);
        this.priceParams = new ResTextParameterReader("price", parameters);
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

    public TextParameterReader getResNameParams() {
        return resNameParams;
    }

    public TextParameterReader getAutNameParams() {
        return autNameParams;
    }

    public TextParameterReader getRevCountParams() {
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

    public TextParameterReader getDlCountParams() {
        return dlCountParams;
    }

    public TextParameterReader getPriceParams() {
        return priceParams;
    }
}
