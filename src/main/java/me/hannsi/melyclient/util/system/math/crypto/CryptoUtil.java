package me.hannsi.melyclient.util.system.math.crypto;

import me.hannsi.melyclient.util.system.debug.DebugLevel;
import me.hannsi.melyclient.util.system.debug.DebugLog;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class CryptoUtil {
    public static SecretKey getKey(String pass) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(pass.getBytes(StandardCharsets.UTF_8));
            byte[] shaKey = sha.digest();
            return new SecretKeySpec(shaKey, "AES");
        } catch (Exception e) {
            new DebugLog(e, DebugLevel.ERROR);
        }
        return null;
    }

    public static String encrypt(String text, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes);
    }
}