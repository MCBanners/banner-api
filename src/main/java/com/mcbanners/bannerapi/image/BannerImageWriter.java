package com.mcbanners.bannerapi.image;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BannerImageWriter {
    private static final ResponseEntity<byte[]> NULL = new ResponseEntity<>(new byte[]{}, HttpStatus.NO_CONTENT);

    public static ResponseEntity<byte[]> write(BufferedImage image, BannerOutputType outputType) {
        final ImageWriter writer = ImageIO.getImageWritersByFormatName(outputType.getName()).next();
        if (writer == null) {
            return NULL;
        }

        ImageWriteParam param = null;
        if (outputType == BannerOutputType.JPEG) {
            JPEGImageWriteParam jpgParam = new JPEGImageWriteParam(null);
            jpgParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgParam.setCompressionQuality(1f);
            param = jpgParam;
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            writer.setOutput(ImageIO.createImageOutputStream(bos));
            writer.write(null, new IIOImage(image, null, null), param);
            bos.flush();
            return new ResponseEntity<>(bos.toByteArray(), HttpStatus.OK);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(new byte[]{}, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
