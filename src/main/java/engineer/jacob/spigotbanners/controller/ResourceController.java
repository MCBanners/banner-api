package engineer.jacob.spigotbanners.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import engineer.jacob.spigotbanners.BannerTemplate;
import engineer.jacob.spigotbanners.BannerTextTheme;
import engineer.jacob.spigotbanners.FontFace;
import engineer.jacob.spigotbanners.spiget.SpigetClient;
import engineer.jacob.spigotbanners.spiget.obj.SpigetResource;
import engineer.jacob.spigotbanners.util.ImageBuilder;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

        BannerTemplate template = BannerTemplate.ORANGE;

        BufferedImage out = ImageBuilder
                .create(template.getImage())
                .text()
                .initialX(10)
                .initialY(50)
                .fontSize(23)
                .color(template.getTextTheme() == BannerTextTheme.DARK ? Color.BLACK : Color.WHITE)
                .content(resource.getName(), FontFace.UBUNTU_TITLE)
                .finishText()
                .build();

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(out, "png", bos);
            bos.flush();
            return new ResponseEntity<>(bos.toByteArray(), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(new byte[] {}, HttpStatus.NO_CONTENT);
        }
    }
}
