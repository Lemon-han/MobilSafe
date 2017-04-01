package com.emma.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/4/1.
 */

public class Md5Util {
    /**
     * @param
     * @return
     */
    public static String encoder(String psd) {
        psd = psd + "mobilesafe";
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] bs = digest.digest(psd.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bs) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
                return stringBuffer.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }
}
