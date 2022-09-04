package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.BackgroundTemplate;
import com.mcbanners.bannerapi.banner.BannerOutputFormat;
import com.mcbanners.bannerapi.banner.ImageBuilder;
import com.mcbanners.bannerapi.banner.TextTheme;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.ImageComponent;
import com.mcbanners.bannerapi.banner.parameter.GlobalParameters;
import com.mcbanners.bannerapi.banner.parameter.api.namespace.TextParameterNamespace;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public abstract class Layout<T extends GlobalParameters> {
    private static final Color light = new Color(230, 224, 224);
    private static final Color dark = new Color(65, 60, 60);

    private final T parameters;
    private final List<BasicComponent> components;
    private final BackgroundTemplate template;

    public Layout(T parameters) {
        this.parameters = parameters;
        this.components = new ArrayList<>();
        template = parameters.getBackground().readTemplate();
    }

    protected final T parameters() {
        return parameters;
    }

    protected final void component(BasicComponent component) {
        components.add(component);
    }

    protected final void text(TextParameterNamespace textParameterNamespace, String content, Object... replacements) {
        component(textParameterNamespace.asTextComponent(textColor(), String.format(content, replacements)));
    }

    protected final void wrappingText(TextParameterNamespace textParameterNamespace, int maxCharacters, String content, Object... replacements) {
        component(textParameterNamespace.asTextComponent(
                textColor(),
                String.format(content, replacements),
                true,
                maxCharacters
        ));
    }

    protected final void date(TextParameterNamespace textParameterNamespace, Instant instant, String prefix) {
        final SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        text(textParameterNamespace, "%s %s", prefix, sdf.format(Date.from(instant)));
    }

    protected final void image(int x, int y, String base64) throws IOException {
        image(x, y, Base64.getDecoder().decode(base64));
    }

    protected final void image(int x, int y, byte[] base64) throws IOException {
        image(x, y, new ByteArrayInputStream(base64));
    }

    protected final void image(int x, int y, ByteArrayInputStream stream) throws IOException {
        image(x, y, ImageIO.read(stream));
    }

    protected final void image(int x, int y, BufferedImage image) {
        component(new ImageComponent(x, y, image));
    }

    protected final List<BasicComponent> components() {
        return components;
    }

    protected final Color textColor() {
        return template.getTextTheme() == TextTheme.LIGHT ? light : dark;
    }

    public final BufferedImage draw(BannerOutputFormat outputFormat) {
        ImageBuilder builder = ImageBuilder.create(template.getImage(), outputFormat);

        for (BasicComponent component : build()) {
            builder = component.draw(builder, outputFormat);
        }

        return builder.build();
    }

    protected abstract List<BasicComponent> build();
}
