package entryPoint;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class InstallApp {

	private static final Path SETUP_DIR = Paths.get("setup");
	private static final Path KEYS_DIR = SETUP_DIR.resolve("keys");
	private static final Path FIRMAS_DIR = SETUP_DIR.resolve("firmas");

	private static final Path DES_KEY_FILE = KEYS_DIR.resolve("des.key");
	private static final Path DSA_PRIVATE_FILE = KEYS_DIR.resolve("dsa_private.key");
	private static final Path DSA_PUBLIC_FILE = KEYS_DIR.resolve("dsa_public.key");

	public static void main(String[] args) {
//		try {
//			createFolders();
//			createDesKeyIfMissing();
//			createDsaKeysIfMissing();
//			System.out.println("InstallApp completed successfully.");
//		} catch (Exception e) {
//			System.out.println("InstallApp failed: " + e.getMessage());
//		}
	}

	private static void createFolders() throws Exception {
		Files.createDirectories(KEYS_DIR);
		Files.createDirectories(FIRMAS_DIR);
		System.out.println("Folders ready: " + KEYS_DIR + " and " + FIRMAS_DIR);
	}

	private static void createDesKeyIfMissing() throws Exception {
		if (Files.exists(DES_KEY_FILE)) {
			System.out.println("DES key already exists.");
			return;
		}

		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(56, new SecureRandom()); // DES key size
		SecretKey key = kg.generateKey();

		String encoded = Base64.getEncoder().encodeToString(key.getEncoded());
		Files.writeString(DES_KEY_FILE, encoded);

		System.out.println("DES key generated: " + DES_KEY_FILE);
	}

	private static void createDsaKeysIfMissing() throws Exception {
		if (Files.exists(DSA_PRIVATE_FILE) && Files.exists(DSA_PUBLIC_FILE)) {
			System.out.println("DSA keys already exist.");
			return;
		}

		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		kpg.initialize(2048, new SecureRandom());
		KeyPair kp = kpg.generateKeyPair();

		String privateB64 = Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded());
		String publicB64 = Base64.getEncoder().encodeToString(kp.getPublic().getEncoded());

		Files.writeString(DSA_PRIVATE_FILE, privateB64);
		Files.writeString(DSA_PUBLIC_FILE, publicB64);

		System.out.println("DSA keypair generated:");
		System.out.println(" - " + DSA_PRIVATE_FILE);
		System.out.println(" - " + DSA_PUBLIC_FILE);
	}
}