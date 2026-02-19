
package entryPoint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class InstallApp {

	private static final File SETUP_DIR = new File("setup");
	private static final File KEYS_DIR = new File(SETUP_DIR, "keys");
	private static final File FIRMAS_DIR = new File(SETUP_DIR, "firmas");
	private static final File SUMMERY_DIR = new File(SETUP_DIR, "summery");

	private static final File DES_KEY_FILE = new File(KEYS_DIR, "des.key");
	private static final File DSA_PRIVATE_FILE = new File(KEYS_DIR, "dsa_private.key");
	private static final File DSA_PUBLIC_FILE = new File(KEYS_DIR, "dsa_public.key");

	public static void main(String[] args) {
		try {
			createFolders();
			createDesKeyIfMissing();
			createDsaKeyIfMissing();
			System.out.println("InstallApp completed successfully.");
		} catch (Exception e) {
			System.out.println("InstallApp failed: " + e.getMessage());
		}
	}

	private static void createDesKeyIfMissing() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
		if (DES_KEY_FILE.exists()) {
			System.out.println("Key already in existence");
			return;
		}
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		SecretKey key = kg.generateKey();

		SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
		DESKeySpec keySpec = (DESKeySpec) factory.getKeySpec(key, DESKeySpec.class);

		FileOutputStream fos = new FileOutputStream(DES_KEY_FILE);
		fos.write(keySpec.getKey());
		fos.close();
		System.out.println("DES key generated and saved to " + DES_KEY_FILE.getPath());
	}

	private static void createDsaKeyIfMissing() throws Exception {
		if (DSA_PRIVATE_FILE.exists() && DSA_PUBLIC_FILE.exists()) {
			System.out.println("DSA keys already exist");
			return;
		}

		// Generate DSA key pair
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
		keyGen.initialize(1024); // Key size
		KeyPair keyPair = keyGen.generateKeyPair();

		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		// Extract DSA parameters
		KeyFactory keyFactory = KeyFactory.getInstance("DSA");
		DSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, DSAPrivateKeySpec.class);
		DSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, DSAPublicKeySpec.class);

		// Save private key
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(DSA_PRIVATE_FILE))) {
			pw.println(privateKeySpec.getG());
			pw.println(privateKeySpec.getP());
			pw.println(privateKeySpec.getQ());
			pw.println(privateKeySpec.getX());
		}

		// Save public key
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(DSA_PUBLIC_FILE))) {
			pw.println(publicKeySpec.getY());
			pw.println(publicKeySpec.getP());
			pw.println(publicKeySpec.getQ());
			pw.println(publicKeySpec.getG());
		}

		System.out.println("DSA keys generated and saved to " + KEYS_DIR.getPath());
	}

	private static void createFolders() {
		if (!SETUP_DIR.exists()) {
			KEYS_DIR.mkdirs();
			FIRMAS_DIR.mkdirs();
			SUMMERY_DIR.mkdirs();
			return;
		}
		if (!KEYS_DIR.exists()) {
			KEYS_DIR.mkdirs();
		}
		if (!FIRMAS_DIR.exists()) {
			FIRMAS_DIR.mkdirs();
		}
		if (!SUMMERY_DIR.exists()) {
			SUMMERY_DIR.mkdirs();
		}
	}

}