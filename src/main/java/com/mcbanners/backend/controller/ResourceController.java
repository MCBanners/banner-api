package com.mcbanners.backend.controller;

import com.mcbanners.backend.*;
import com.mcbanners.backend.spiget.SpigetClient;
import com.mcbanners.backend.spiget.obj.SpigetAuthor;
import com.mcbanners.backend.spiget.obj.SpigetResource;
import com.mcbanners.backend.util.ImageBuilder;
import com.mcbanners.backend.util.ImageUtil;
import com.mcbanners.backend.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("resource")
public class ResourceController {
    private final SpigetClient client;

    @Value("${banner.dark_text_color}")
    private int[] darkTextRgb;

    @Value("${banner.light_text_color}")
    private int[] lightTextRgb;

    @Autowired
    public ResourceController(SpigetClient client) {
        this.client = client;
    }

    @GetMapping(value = "/{id}/banner.png", produces = "image/png")
    public ResponseEntity<byte[]> getBanner(@PathVariable int id, @RequestParam Map<String, String> raw) {
        SpigetResource resource = client.getResource(id).getBody();
        if (resource == null) {
            return null;
        }

        SpigetAuthor author = client.getAuthor(resource.getAuthor().getId()).getBody();
        if (author == null) {
            return null;
        }

        Map<BannerParameter, Object> params = BannerParameter.parse(raw);

        BannerTemplate template = (BannerTemplate) params.get(BannerParameter.TEMPLATE);
        Color textColor = template.getTextTheme() == BannerTextTheme.DARK ?
                new Color(darkTextRgb[0], darkTextRgb[1], darkTextRgb[2]) :
                new Color(lightTextRgb[0], lightTextRgb[1], lightTextRgb[2]);

        ImageBuilder builder = ImageBuilder.create(template.getImage());

        // Resource Logo
        try {
            BufferedImage resourceLogo = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(resource.getIcon().getData())));

            int resourceLogoSize = Math.min((int) params.get(BannerParameter.LOGO_SIZE), 96);
            if (resourceLogoSize < 96) {
                resourceLogo = ImageUtil.resize(resourceLogo, resourceLogoSize, resourceLogoSize);
            }

            int x = (int) params.get(BannerParameter.LOGO_X);
            int y = (100 - resourceLogoSize) / 2;

            builder = builder.overlayImage(resourceLogo, x, y);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Resource Name
        builder = builder.text()
                .initialX((int) params.get(BannerParameter.RES_NAME_X))
                .initialY((int) params.get(BannerParameter.RES_NAME_Y))
                .fontSize((int) params.get(BannerParameter.RES_NAME_FONT_SIZE))
                .color(textColor)
                .bold((boolean) params.get(BannerParameter.RES_NAME_BOLD))
                .align((BannerTextAlign) params.get(BannerParameter.RES_NAME_TEXT_ALIGN))
                .content(resource.getName(), (FontFace) params.get(BannerParameter.RES_NAME_FONT))
                .finishText();

        // Author Name
        builder = builder.text()
                .initialX((int) params.get(BannerParameter.AUT_NAME_X))
                .initialY((int) params.get(BannerParameter.AUT_NAME_Y))
                .fontSize((int) params.get(BannerParameter.AUT_NAME_FONT_SIZE))
                .color(textColor)
                .bold((boolean) params.get(BannerParameter.AUT_NAME_BOLD))
                .align((BannerTextAlign) params.get(BannerParameter.AUT_NAME_TEXT_ALIGN))
                .content(String.format("by %s", author.getName()), (FontFace) params.get(BannerParameter.AUT_NAME_FONT))
                .finishText();

        // Review Count
        builder = builder.text()
                .initialX((int) params.get(BannerParameter.REV_COUNT_X))
                .initialY((int) params.get(BannerParameter.REV_COUNT_Y))
                .fontSize((int) params.get(BannerParameter.REV_COUNT_FONT_SIZE))
                .color(textColor)
                .bold((boolean) params.get(BannerParameter.REV_COUNT_BOLD))
                .align((BannerTextAlign) params.get(BannerParameter.REV_COUNT_TEXT_ALIGN))
                .content(String.format("%s reviews", NumberUtil.abbreviate(resource.getRating().getCount())), (FontFace) params.get(BannerParameter.REV_COUNT_FONT))
                .finishText();

        // Stars
        BufferedImage star = null, halfStar = null, emptyStar = null;
        try {
            star = ImageUtil.resize(ImageIO.read(getClass().getResourceAsStream("/sprites/star.png")), 12, 12);
            halfStar = ImageUtil.resize(ImageIO.read(getClass().getResourceAsStream("/sprites/star_half.png")), 12, 12);
            emptyStar = ImageUtil.resize(ImageIO.read(getClass().getResourceAsStream("/sprites/star_off.png")), 12, 12);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (star != null && halfStar != null && emptyStar != null) {
            double ratingAvg = resource.getRating().getAverage();
            double gap = (double) params.get(BannerParameter.STARS_GAP);
            for (int i = 0; i < 5; i++) {
                BufferedImage toOverlay;

                if (ratingAvg >= 1) {
                    ratingAvg--;
                    toOverlay = star;
                } else if (ratingAvg >= 0.5) {
                    ratingAvg -= 0.5;
                    toOverlay = halfStar;
                } else {
                    toOverlay = emptyStar;
                }

                builder = builder.overlayImage(toOverlay, ((int) params.get(BannerParameter.STARS_X)) + ((int) gap * i), (int) params.get(BannerParameter.STARS_Y));
            }
        }

        // Download Count
        builder = builder.text()
                .initialX((int) params.get(BannerParameter.DL_COUNT_X))
                .initialY((int) params.get(BannerParameter.DL_COUNT_Y))
                .fontSize((int) params.get(BannerParameter.DL_COUNT_FONT_SIZE))
                .color(textColor)
                .bold((boolean) params.get(BannerParameter.DL_COUNT_BOLD))
                .align((BannerTextAlign) params.get(BannerParameter.DL_COUNT_TEXT_ALIGN))
                .content(String.format("%s downloads", NumberUtil.abbreviate(resource.getDownloads())), (FontFace) params.get(BannerParameter.DL_COUNT_FONT))
                .finishText();

        // Price Tag
        if (resource.isPremium()) {
            builder = builder.text()
                    .initialX((int) params.get(BannerParameter.PRICE_X))
                    .initialY((int) params.get(BannerParameter.PRICE_Y))
                    .fontSize((int) params.get(BannerParameter.PRICE_FONT_SIZE))
                    .color(textColor)
                    .bold((boolean) params.get(BannerParameter.PRICE_BOLD))
                    .align((BannerTextAlign) params.get(BannerParameter.PRICE_TEXT_ALIGN))
                    .content(String.format("%.2f %s", resource.getPrice(), resource.getCurrency()), (FontFace) params.get(BannerParameter.PRICE_FONT))
                    .finishText();
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(builder.build(), "png", bos);
            bos.flush();
            return new ResponseEntity<>(bos.toByteArray(), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(new byte[] {}, HttpStatus.NO_CONTENT);
        }
    }
}
