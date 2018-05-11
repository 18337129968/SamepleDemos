package com.hfxief.utils.md5;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * maimai_a
 * cn.maitian.app.util
 *
 * @Author: xie
 * @Time: 2016/7/28 13:28
 * @Description:
 */

public class MD5Utils extends MD5Util {

    private static String toString(byte[] encryption) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < encryption.length; i++) {
            if (toHexString(encryption[i]).length() == 1) {
                strBuf.append("0").append(toHexString(encryption[i]));
            } else {
                strBuf.append(toHexString(encryption[i]));
            }
        }
        return strBuf.toString();
    }

    private static String toHexString(byte b) {
        return Integer.toHexString(0xff & b);
    }

    /**
     * @return java.lang.String
     * @Title: md5String
     * @Description: (md5字符加密)
     * @params [str]
     */
    public static String md5String(String str) {
        byte[] encryption = md5(str);
        return toString(encryption);
    }


    /**
     * @return java.lang.String
     * @Title: md5File
     * @Description: (md5文件校验)
     * @params [filename]
     */
    public static String md5File(String filename) {
        InputStream fis;
        String md5;
        try {
            fis = new FileInputStream(filename);
            md5 = toString(md5(fis));
            fis.close();
            return md5;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
