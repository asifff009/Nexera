package com.asif.stepupbd;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MyMethods {


    public static String MY_KEY = "";



    //=======================================================================

    public static String encryptData(String text, String pass) throws Exception {
        // Ensure key is 16 bytes (128-bit AES)
        byte[] passwordBytes = pass.getBytes("UTF-8");
        if (passwordBytes.length != 16) {
            throw new IllegalArgumentException("Password must be exactly 16 characters long.");
        }

        // Prepare secret key
        SecretKeySpec secretKey = new SecretKeySpec(passwordBytes, "AES");

        // Initialize AES cipher
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Encrypt the plaintext
        byte[] plainTextBytes = text.getBytes("UTF-8");
        byte[] encryptedBytes = cipher.doFinal(plainTextBytes);

        // Encode as Base64 string
        return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP);
    }
}
