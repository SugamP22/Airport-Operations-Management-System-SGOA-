package utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesUtil {

    private static final String TRANSFORMATION = "DES/ECB/PKCS5Padding";
    private static final String ALGORITHM = "DES";
    private static final String KEY_PROPERTY = "security.des.key";
    private static String desKey;

    static {
        try (InputStream in = DesUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new RuntimeException("db.properties not found");
            }
            Properties props = new Properties();
            props.load(in);

            desKey = props.getProperty(KEY_PROPERTY);
            if (desKey == null || desKey.length() != 8) {
                throw new RuntimeException("DES key must exist and be exactly 8 characters");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not load DES key", e);
        }
    }

    private static SecretKey buildKey() throws Exception {
        DESKeySpec keySpec = new DESKeySpec(desKey.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(keySpec);
    }

    public static String encrypt(String plainText) {
        if (plainText == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, buildKey());
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("DES encryption failed", e);
        }
    }

    public static String decrypt(String encryptedText) {
        if (encryptedText == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, buildKey());
            byte[] decoded = Base64.getDecoder().decode(encryptedText);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("DES decryption failed", e);
        }
    }
}