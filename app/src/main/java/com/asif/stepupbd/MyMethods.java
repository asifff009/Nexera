package com.asif.stepupbd;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MyMethods {

    public static String MY_KEY = "";

    // Encrypt a plaintext string and return AES-ECB encrypted Base64 string
    public static String encryptData(String text, String pass) throws Exception {
        // Must be 16 characters for AES-128
        byte[] passwordBytes = pass.getBytes("UTF-8");
        if (passwordBytes.length != 16) {
            throw new IllegalArgumentException("Password must be exactly 16 characters.");
        }

        SecretKeySpec secretKey = new SecretKeySpec(passwordBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // Java uses PKCS5/PKCS7 compatible
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] plainTextBytes = text.getBytes("UTF-8");
        byte[] encryptedBytes = cipher.doFinal(plainTextBytes);

        return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP);
    }
}
