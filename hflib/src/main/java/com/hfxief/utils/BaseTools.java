package com.hfxief.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseTools {
    public static Dialog dialog;

    /**
     * 获取屏幕【宽度】的像素数
     *
     * @param activity
     * @return
     */
    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕【高度】的像素数
     *
     * @param activity
     * @return
     */
    public final static int getWindowsHeigh(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 检查邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * double型返回String，小数点后保留2位
     *
     * @param value
     * @return
     */
    public static String getDecimal(Double value) {
        if (null != value) {
            DecimalFormat df = new DecimalFormat("###,###.##");
            return df.format(value);
        }
        return "0";
    }

    /**
     * 价钱单位换算
     */
    public static String getPrice(int price) {
        return price / 100 + "." + (price % 100 == 0 ? "00" : (price % 100 < 10 ? ("0" + price % 100) : price % 100));
    }
}
