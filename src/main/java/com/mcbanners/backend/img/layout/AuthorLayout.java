package com.mcbanners.backend.img.layout;

import com.mcbanners.backend.banner.BannerSprite;
import com.mcbanners.backend.banner.param.TextParameterReader;
import com.mcbanners.backend.banner.param.author.AuthorParameterReader;
import com.mcbanners.backend.banner.param.author.AuthorParamter;
import com.mcbanners.backend.img.ImageBuilder;
import com.mcbanners.backend.img.component.Component;
import com.mcbanners.backend.img.component.ImageComponent;
import com.mcbanners.backend.obj.Author;
import com.mcbanners.backend.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class AuthorLayout extends Layout {
    private final Author author;
    private final AuthorParameterReader parameters;

    public AuthorLayout(Author author, Map<AuthorParamter, Object> parameters) {
        this.author = author;

        this.parameters = new AuthorParameterReader(parameters);
    }

    public List<Component> getComponents() {
        List<Component> components = new ArrayList<>();
        Color textColor = getTextColor(parameters.getTemplate());

        try {
            BufferedImage authorLogo = BannerSprite.DEFAULT_SPIGOT_RES_LOGO.getImage();

            String logo = author.getIcon();
            if (!logo.isEmpty()) {
                BufferedImage temp = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(author.getIcon())));
                if (temp != null) {
                    authorLogo = temp;
                }
            }

            if (authorLogo == null) {
                // literally everything failed including fallback logo if we're in here
                throw new RuntimeException("Could not load real or fallback logo for banner, giving up");
            }

            int authorLogoSize = Math.min(parameters.getLogoSize(), 96);
            if (authorLogoSize < 96) {
                authorLogo = ImageUtil.resize(authorLogo, authorLogoSize, authorLogoSize);
            }

            components.add(new ImageComponent(
                    parameters.getLogoX(),
                    (100 - authorLogoSize) / 2,
                    authorLogo
            ));
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
        }

        TextParameterReader name = parameters.getAutNameParams();
        components.add(name.makeComponent(textColor, "Author: " + author.getName()));

        TextParameterReader res = parameters.getResAmountParams();
        components.add(res.makeComponent(textColor, String.valueOf(author.getResources()) + " resources"));

        return components;
    }

    @Override
    public BufferedImage draw() {
        ImageBuilder builder = ImageBuilder.create(parameters.getTemplate().getImage());

        for (Component component : getComponents()) {
            builder = component.draw(builder);
        }

        return builder.build();
    }
}
