package com.example.follow.utils;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/15
 **/
public class TextUtil {
    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }
}
