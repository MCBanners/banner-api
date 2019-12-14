package engineer.jacob.spigotbanners.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import engineer.jacob.spigotbanners.BannerTemplate;
import engineer.jacob.spigotbanners.BannerTextAlign;
import engineer.jacob.spigotbanners.BannerTextTheme;
import engineer.jacob.spigotbanners.FontFace;
import engineer.jacob.spigotbanners.spiget.SpigetClient;
import engineer.jacob.spigotbanners.spiget.obj.SpigetAuthor;
import engineer.jacob.spigotbanners.spiget.obj.SpigetResource;
import engineer.jacob.spigotbanners.util.ImageBuilder;
import engineer.jacob.spigotbanners.util.ImageUtil;
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

        BannerTemplate template = BannerTemplate.ORANGE;
        Color textColor = template.getTextTheme() == BannerTextTheme.DARK ? Color.BLACK : Color.WHITE;

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
                .align(BannerTextAlign.LEFT)
                .content(resource.getName(), FontFace.FORQUE)
                .finishText();

        // Author Name
        builder = builder.text()
                .initialX(80)
                .initialY(50)
                .fontSize(16)
                .color(textColor)
                .align(BannerTextAlign.LEFT)
                .content(String.format("by %s", author.getName()), FontFace.FORQUE)
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

                builder = builder.overlayImage(toOverlay, 80 + ((int) gap * i), 55);
            }
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
