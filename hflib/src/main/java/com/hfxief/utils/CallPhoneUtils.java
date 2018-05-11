package com.hfxief.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.hfxief.R;
import com.hfxief.app.BaseManagers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallPhoneUtils {

    /**
     * @return void
     * @see: CallPhone
     * @Description: (打电话)
     * @params [context, phoneNumber]
     * @since 2.0
     */
    public static void CallPhone(Context context, String phoneNumber) {
        String phoneNum = judgePhoneNUm(context, phoneNumber);
        if (TextUtils.isEmpty(phoneNum)) {
            BaseManagers.getToastor().showSingletonToast(context.getString(R.string.not_phone_num));
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void SendMsg(Context context, String phoneNumber) {
        String phoneNum = judgePhoneNUm(context, phoneNumber);
        if (TextUtils.isEmpty(phoneNum)) {
            BaseManagers.getToastor().showSingletonToast(context.getString(R.string.not_phone_num));
            return;
        }
        Uri smsToUri = Uri.parse("smsto:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        context.startActivity(intent);
    }

    /**
     * @return java.lang.String
     * @see: judgePhoneNUm
     * @Description: (判断号码)
     * @params [context, phoneNumber]
     * @since 2.0
     */
    private static String judgePhoneNUm(Context context, String phoneNumber) {
        String[] split = phoneNumber.split("-");
        if (split.length > 2) {
            phoneNumber = split[0] + split[1];
        }
        String phoneNum = getPhoneNumber(!TextUtils.isEmpty(phoneNumber) ? phoneNumber : "");
        return phoneNum;
    }

    /**
     * @return java.lang.String
     * @see: getPhoneNumber
     * @Description: (正则判断号码)
     * @params [phoneNumber]
     * @since 2.0
     */
    private static String getPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("-", "");
        Pattern p = Pattern.compile("(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{3,14}");        //	(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}
        Matcher m = p.matcher(phoneNumber);
        if (m.find()) {
            return m.group();
        }
        return null;
    }

}
