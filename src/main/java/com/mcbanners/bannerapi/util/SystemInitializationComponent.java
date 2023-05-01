package com.mcbanners.bannerapi.util;

import com.mcbanners.bannerapi.banner.FontFace;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class SystemInitializationComponent {
    @PostConstruct
    public void run() {
        copyFontFiles();
    }

    private void copyFontFiles() {
        File directory = new File("fonts");
        if (!directory.exists() && !directory.mkdir()) {
            throw new RuntimeException("Failed to create fonts directory on disk.");
        }

        for (FontFace face : FontFace.values()) {
            try {
                File regular = face.getFile(false);
                if (!regular.exists()) {
                    Files.copy(getClass().getResourceAsStream("/fonts/" + face.getFileName(false)), regular.toPath());
                }

                File bold = face.getFile(true);
                if (!bold.exists()) {
                    Files.copy(getClass().getResourceAsStream("/fonts/" + face.getFileName(true)), bold.toPath());
                }
            } catch (IOException e) {
                System.err.println("Failed to copy file(s) for font " + face.name());
                e.printStackTrace();
            }
        }
    }
}
