package com.example.follow.utils;

/**
 * @author LYF
 * @dec:
 * @date 2023/8/9
 **/

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class SymmetricEncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "abpfdff328dfdrf0"; // Replace with your own key

    public static String encrypt(String data) throws Exception {
        SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.encodeBase64String(encryptedBytes);
    }

    public static String decrypt(String encryptedData) throws Exception {
        SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encryptedData));
        return new String(decryptedBytes);
    }

    public static String encrypt(String key, String data) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.encodeBase64String(encryptedBytes);
    }

    public static String[] generateRandomEncrypt(String data) throws Exception {
        String[] backData = new String[2];
        String key = generateRandomKey(16);
        String encrypt = encrypt(key, data);
        backData[0] = key;
        backData[1] = encrypt;
        return backData;
    }

    public static String generateRandomKey(int keyLength) {
        final String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder key = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < keyLength; i++) {
            int randomIndex = secureRandom.nextInt(charset.length());
            char randomChar = charset.charAt(randomIndex);
            key.append(randomChar);
        }

        return key.toString();
    }

    public static void main(String[] args) {
        String text = "刘宇飞爱死梁媛媛了！";
        try {
            String encryptText = encrypt(text);
            System.out.println("加密后：" + encryptText);
            String decryptText = decrypt(encryptText);
            System.out.println("解密后：" + decryptText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
