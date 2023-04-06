package com.mcbanners.bannerapi.banner.parameter;

import com.mcbanners.bannerapi.banner.BackgroundTemplate;
import com.mcbanners.bannerapi.banner.parameter.api.namespace.ParameterNamespace;
import com.mcbanners.bannerapi.banner.parameter.api.namespace.SquareSizeableParameterNamespace;
import com.mcbanners.bannerapi.banner.parameter.api.type.EnumParameter;
import com.mcbanners.bannerapi.banner.parameter.api.type.Parameter;

import java.util.HashMap;
import java.util.Map;

public class GlobalParameters {
    private final BackgroundParameterNamespace background;
    private final SquareSizeableParameterNamespace logo;

    public GlobalParameters(Map<String, String> rawParameters) {
        this.background = new BackgroundParameterNamespace(rawParameters);
        this.logo = new SquareSizeableParameterNamespace("logo", rawParameters);

        // Logo defaults
        logo.getX().defaultValue(12);
        logo.getSize().defaultValue(80);
    }

    public BackgroundParameterNamespace getBackground() {
        return background;
    }

    public final SquareSizeableParameterNamespace getLogo() {
        return logo;
    }

    public Map<String, Map<String, Object>> defaults() {
        Map<String, Map<String, Object>> output = new HashMap<>();

        output.put("background", background.defaults());
        output.put("logo", logo.defaults());

        return output;
    }

    public static final class BackgroundParameterNamespace extends ParameterNamespace {
        private final Parameter<BackgroundTemplate> template;

        public BackgroundParameterNamespace(Map<String, String> rawParameters) {
            super("background", rawParameters);
            this.template = new EnumParameter<>("template", BackgroundTemplate.class, BackgroundTemplate.MOONLIGHT_PURPLE);
        }

        public Parameter<BackgroundTemplate> getTemplate() {
            return template;
        }

        public BackgroundTemplate readTemplate() {
            return read(template);
        }

        public Map<String, Object> defaults() {
            Map<String, Object> output = new HashMap<>();

            output.put("template", template.defaultValue());

            return output;
        }
    }
}
