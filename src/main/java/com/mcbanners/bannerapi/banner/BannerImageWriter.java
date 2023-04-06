package com.mcbanners.bannerapi.banner;

import com.mcbanners.bannerapi.banner.layout.Layout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BannerImageWriter {
    private static final ResponseEntity<byte[]> NULL = new ResponseEntity<>(new byte[]{}, HttpStatus.NO_CONTENT);

    public static ResponseEntity<byte[]> write(Layout<?> layout, BannerOutputFormat format) {
        final ImageWriter writer = ImageIO.getImageWritersByFormatName(format.getName()).next();
        if (writer == null) {
            return NULL;
        }

        ImageWriteParam param = null;
        if (format == BannerOutputFormat.JPEG) {
            JPEGImageWriteParam jpgParam = new JPEGImageWriteParam(null);
            jpgParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgParam.setCompressionQuality(1f);
            param = jpgParam;
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            writer.setOutput(ImageIO.createImageOutputStream(bos));
            writer.write(null, new IIOImage(layout.draw(format), null, null), param);
            bos.flush();
            return new ResponseEntity<>(bos.toByteArray(), HttpStatus.OK);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(new byte[]{}, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
