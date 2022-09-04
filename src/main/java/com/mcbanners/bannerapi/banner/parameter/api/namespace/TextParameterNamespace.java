package com.mcbanners.bannerapi.banner.parameter.api.namespace;

import com.mcbanners.bannerapi.banner.FontFace;
import com.mcbanners.bannerapi.banner.TextAlign;
import com.mcbanners.bannerapi.banner.component.TextComponent;
import com.mcbanners.bannerapi.banner.component.WrappableTextComponent;
import com.mcbanners.bannerapi.banner.parameter.api.type.BooleanParameter;
import com.mcbanners.bannerapi.banner.parameter.api.type.EnumParameter;
import com.mcbanners.bannerapi.banner.parameter.api.type.IntegerParameter;
import com.mcbanners.bannerapi.banner.parameter.api.type.Parameter;
import com.mcbanners.bannerapi.banner.parameter.api.type.StringParameter;

import java.awt.*;
import java.util.Map;

public class TextParameterNamespace extends OrientableParameterNamespace {
    private final Parameter<Integer> fontSize;
    private final Parameter<Boolean> fontBold;
    private final Parameter<FontFace> fontFace;
    private final Parameter<TextAlign> textAlign;
    private final Parameter<String> display;
    private final Parameter<Boolean> enable;
    private final Parameter<Integer> maximumCharacters;

    public TextParameterNamespace(String namespace, Map<String, String> rawParameters) {
        super(namespace, rawParameters);
        this.fontSize = new IntegerParameter("font_size", 14);
        this.fontBold = new BooleanParameter("font_bold", false);
        this.fontFace = new EnumParameter<>("font_face", FontFace.class, FontFace.SOURCE_SANS_PRO);
        this.textAlign = new EnumParameter<>("text_align", TextAlign.class, TextAlign.LEFT);
        this.display = new StringParameter("display", "");
        this.enable = new BooleanParameter("enable", true);
        this.maximumCharacters = new IntegerParameter("max_chars", 9999);
    }

    public final Parameter<Integer> getFontSize() {
        return fontSize;
    }

    public final Integer readFontSize() {
        return read(fontSize);
    }

    public final Parameter<Boolean> getFontBold() {
        return fontBold;
    }

    public final Boolean readFontBold() {
        return read(fontBold);
    }

    public final Parameter<FontFace> getFontFace() {
        return fontFace;
    }

    public final FontFace readFontFace() {
        return read(fontFace);
    }

    public final Parameter<TextAlign> getTextAlign() {
        return textAlign;
    }

    public final TextAlign readTextAlign() {
        return read(textAlign);
    }

    public Parameter<String> getDisplay() {
        return display;
    }

    public String readDisplay() {
        return read(display);
    }

    public Parameter<Boolean> getEnable() {
        return enable;
    }

    public Boolean readEnable() {
        return read(enable);
    }

    public Parameter<Integer> getMaximumCharacters() {
        return maximumCharacters;
    }

    public Integer readMaxCharacters() {
        return read(maximumCharacters);
    }

    public final TextComponent asTextComponent(Color textColor, String content) {
        return asTextComponent(textColor, content, false, null);
    }

    public final TextComponent asTextComponent(Color textColor, String content, boolean wrapText, Integer maxLength) {
        TextComponent component = new TextComponent(
                readX(),
                readY(),
                readFontSize(),
                textColor,
                readFontBold(),
                readTextAlign(),
                readFontFace(),
                content
        );

        return wrapText ? new WrappableTextComponent(component, maxLength) : component;
    }

    @Override
    public Map<String, Object> defaults() {
        Map<String, Object> output = super.defaults();

        output.put("font_size", fontSize.defaultValue());
        output.put("bold", fontBold.defaultValue());
        output.put("font_face", fontFace.defaultValue());
        output.put("text_align", textAlign.defaultValue());
        output.put("display", display.defaultValue());
        output.put("enable", enable.defaultValue());
        output.put("max_chars", maximumCharacters.defaultValue());

        return output;
    }
}
