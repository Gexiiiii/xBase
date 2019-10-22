package com.gexiiiii.base.utils;

import java.util.regex.Pattern;

/**
 * author : Gexiiiii
 * date : 2019/10/22 10:04
 * description :
 */
public class XFormValidationUtil {

    /**
     * 手机号校验
     */
    public static boolean isMobile(String phone) {
        if (phone == null) {
            return false;
        }
        // 如果手机中有+86则会自动替换掉
        return validation("^[1][3,4,5,7,8][0-9]{9}$", phone.replace("+86", ""));
    }

    /**
     * 邮箱校验
     */
    public static boolean isEmail(String mail) {
        return validation("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", mail);
    }

    /**
     * 正则校验
     */
    private static boolean validation(String pattern, String str) {
        if (str == null) {
            return false;
        }
        return Pattern.compile(pattern).matcher(str).matches();
    }
}
