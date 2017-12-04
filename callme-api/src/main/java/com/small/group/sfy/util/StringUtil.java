package com.small.group.sfy.util;

/**
 * Created by yq on 2017/12/4.
 */
public class StringUtil {

    public static boolean isNull(String str) {
        return str == null || str.equals("") || str.equalsIgnoreCase("Null");
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }
}
