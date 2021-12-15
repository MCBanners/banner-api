package com.mcbanners.bannerapi.util;

import com.mcbanners.bannerapi.BannerApiApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger logger = LoggerFactory.getLogger(BannerApiApplication.class);

    public static void info(String message, Object... args) {
        logger.info(fmt(message, args));
    }

    public static void warn(String message, Object... args) {
        logger.warn(fmt(message, args));
    }

    public static void error(String message, Object... args) {
        logger.error(fmt(message, args));
    }

    private static String fmt(String message, Object... args) {
        return String.format(message, args);
    }
}
