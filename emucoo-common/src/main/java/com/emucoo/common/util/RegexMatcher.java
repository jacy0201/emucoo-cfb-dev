package com.emucoo.common.util;

import java.util.regex.Pattern;

public class RegexMatcher {

    private static final Pattern phoneNumPattern = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$");
    private static final Pattern emailPattern = Pattern.compile("^[a-z0-9A-Z][a-z0-9A-Z_\\.-]+@([a-z0-9A-Z_-]+\\.)+[a-zA-Z]{2,}$");

    public static boolean isPhoneNumber(String str) {
        return phoneNumPattern.matcher(str).matches();
    }

    public static boolean isEmail(String str) {
        return emailPattern.matcher(str).matches();
    }
}
