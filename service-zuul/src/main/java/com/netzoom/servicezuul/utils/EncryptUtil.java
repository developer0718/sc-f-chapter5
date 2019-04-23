package com.netzoom.servicezuul.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    private static final String[] hexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public EncryptUtil() {
    }

    private static String byteArrayToHexString(byte[] byteArray) {
        StringBuffer sb = new StringBuffer();
        byte[] arr$ = byteArray;
        int len$ = byteArray.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte byt = arr$[i$];
            sb.append(byteToHexString(byt));
        }

        return sb.toString();
    }

    private static String byteToHexString(byte byt) {
        int n = byt;
        if (byt < 0) {
            n = 256 + byt;
        }

        return hexDigits[n / 16] + hexDigits[n % 16];
    }

    public static String Encode(String code, String message) {
        String encode = null;

        try {
            MessageDigest md = MessageDigest.getInstance(code);
            encode = byteArrayToHexString(md.digest(message.getBytes()));
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        }

        return encode;
    }

    public static String md5Encode(String message) {
        return Encode("MD5", message);
    }

    public static String shaEncode(String message) {
        return Encode("SHA", message);
    }

    public static String sha256Encode(String message) {
        return Encode("SHA-256", message);
    }

}
