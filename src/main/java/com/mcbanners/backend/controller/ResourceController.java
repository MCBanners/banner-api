package com.mcbanners.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mcbanners.backend.BannerTemplate;
import com.mcbanners.backend.BannerTextAlign;
import com.mcbanners.backend.BannerTextTheme;
import com.mcbanners.backend.FontFace;
import com.mcbanners.backend.spiget.SpigetClient;
import com.mcbanners.backend.spiget.obj.SpigetAuthor;
import com.mcbanners.backend.spiget.obj.SpigetResource;
import com.mcbanners.backend.util.ImageBuilder;
import com.mcbanners.backend.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("resource")
public class ResourceController {
    private final SpigetClient client;

    @Autowired
    public ResourceController(SpigetClient client) {
        this.client = client;
    }

    @GetMapping(value = "/{id}/banner.png", produces = "image/png")
    public ResponseEntity<byte[]> getBanner(@PathVariable int id) throws JsonProcessingException {
        SpigetResource resource = client.getResource(id).getBody();
        if (resource == null) {
            return null;
        }

        SpigetAuthor author = client.getAuthor(resource.getAuthor().getId()).getBody();
        if (author == null) {
            return null;
        }

        BannerTemplate template = BannerTemplate.BLACK_BRICK;
        Color textColor = template.getTextTheme() == BannerTextTheme.DARK ? new Color(65, 60, 60) : new Color(230, 224, 224);

        ImageBuilder builder = ImageBuilder.create(template.getImage());

        // Resource Logo
        try {
            BufferedImage resourceLogo = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(resource.getIcon().getData())));
            builder = builder.overlayImage(ImageUtil.resize(resourceLogo, 64, 64), 8, 18);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Resource Name
        builder = builder.text()
                .initialX(80)
                .initialY(32)
                .fontSize(23)
                .color(textColor)
                .bold(true)
                .align(BannerTextAlign.LEFT)
                .content(resource.getName(), FontFace.COOLVETICA)
                .finishText();

        // Author Name
        builder = builder.text()
                .initialX(80)
                .initialY(47)
                .fontSize(16)
                .color(textColor)
                .align(BannerTextAlign.LEFT)
                .content(String.format("by %s", author.getName()), FontFace.COOLVETICA)
                .finishText();

        // Stars
        BufferedImage star = null, halfStar = null, emptyStar = null;
        try {
            star = ImageUtil.resize(ImageIO.read(getClass().getResourceAsStream("/sprites/star.png")), 16, 16);
            halfStar = ImageUtil.resize(ImageIO.read(getClass().getResourceAsStream("/sprites/star_half.png")), 16, 16);
            emptyStar = ImageUtil.resize(ImageIO.read(getClass().getResourceAsStream("/sprites/star_off.png")), 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (star != null && halfStar != null && emptyStar != null) {
            double ratingAvg = resource.getRating().getAverage();
            double gap = 16;
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

                builder = builder.overlayImage(toOverlay, 80 + ((int) gap * i), 53);
            }
        }

        // Download Count
        builder = builder.text()
                .initialX(80)
                .initialY(87)
                .fontSize(16)
                .color(textColor)
                .align(BannerTextAlign.LEFT)
                .content(String.format("%d downloads | %d reviews", resource.getDownloads(), resource.getReviews().size()), FontFace.COOLVETICA)
                .finishText();

        // Price Tag
        if (resource.isPremium()) {
            builder = builder.text()
                    .initialX(210)
                    .initialY(62)
                    .fontSize(24)
                    .color(textColor)
                    .bold(true)
                    .align(BannerTextAlign.LEFT)
                    .content(String.format("%.2f %s", resource.getPrice(), resource.getCurrency()), FontFace.COOLVETICA)
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
