package org.edu.reksoft.utils;

public final class UrlUtils {

    private UrlUtils() {
        //NOP
    }

    public static String createUrl(String pattern, Object... params) {
        return String.format(pattern, params);
    }
}