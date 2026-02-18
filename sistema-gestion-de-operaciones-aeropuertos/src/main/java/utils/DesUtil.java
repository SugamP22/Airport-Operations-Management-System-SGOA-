package utils;

/**
 * Small utility I use to encrypt and decrypt sensitive passenger fields
 * (passport, email, phone) with DES, loading the key from setup/keys/des.key.
 */

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesUtil {

	private static final String ALGORITHM = "DES";
	private static final File KEY_FILE = new File("setup/keys/des.key");
	private static final int DES_KEY_SIZE = 8;

	/** Loaded key is kept here after loadKey(). */
	private static SecretKey key;

	public static void loadKey() {
		if (key != null) {
			return;
		}
		if (!KEY_FILE.exists()) {
			throw new RuntimeException("Encryption not available: key file not found. Run InstallApp first.");
		}
		try (FileInputStream fis = new FileInputStream(KEY_FILE)) {
			byte[] keyBytes = new byte[DES_KEY_SIZE];
			int n = fis.read(keyBytes);
			if (n != DES_KEY_SIZE) {
				throw new RuntimeException("DES key file must be exactly " + DES_KEY_SIZE + " bytes");
			}
			DESKeySpec spec = new DESKeySpec(keyBytes);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
			key = factory.generateSecret(spec);
		} catch (Exception e) {
			throw new RuntimeException("Could not load DES key: " + e.getMessage(), e);
		}
	}

	public static String encrypt(String plainText) {
		if (plainText == null) {
			return null;
		}
		loadKey();
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			throw new RuntimeException("DES encrypt failed: " + e.getMessage(), e);
		}
	}

	public static String decrypt(String base64Cipher) {
		if (base64Cipher == null || base64Cipher.isEmpty()) {
			return null;
		}
		loadKey();
		try {
			byte[] encrypted = Base64.getDecoder().decode(base64Cipher);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decrypted = cipher.doFinal(encrypted);
			return new String(decrypted, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException("DES decrypt failed: " + e.getMessage(), e);
		}
	}
}
