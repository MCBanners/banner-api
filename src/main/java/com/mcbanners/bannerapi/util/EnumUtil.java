package com.mcbanners.bannerapi.util;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class EnumUtil {
    public static <T extends Enum<T>> T stringToConst(String string, Class<T> enumClass) throws NoSuchElementException {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(enumConst -> enumConst.name().equalsIgnoreCase(string))
                .findFirst().orElseThrow();
    }
}
