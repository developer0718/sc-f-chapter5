package com.netzoom.servicezuul.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
    public Base64Util() {
    }

    public static String encodeBase64(byte[] src) {
        Base64 base64 = new Base64();
        return new String(base64.encode(src));
    }

    public static String encodeBase64(String src) {
        return encodeBase64(src.getBytes());
    }

    public static byte[] decodeBase64(String src) {
        Base64 base64 = new Base64();
        return base64.decode(src);
    }

    public static byte[] decodeBase64(byte[] src) {
        Base64 base64 = new Base64();
        return base64.decode(src);
    }
}