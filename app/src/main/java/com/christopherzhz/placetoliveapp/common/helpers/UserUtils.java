package com.christopherzhz.placetoliveapp.common.helpers;

import android.text.TextUtils;

public class UserUtils {

    public static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public static boolean isPasswordValid(String pswd) {
        return pswd.length() >= 6;
    }

    public static String generateUserName(String email) {
        return email.substring(0, TextUtils.indexOf(email, '@'));
    }

}
