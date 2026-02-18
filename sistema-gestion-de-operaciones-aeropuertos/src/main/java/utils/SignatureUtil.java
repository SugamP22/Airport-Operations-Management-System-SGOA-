package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.util.Base64;

import entities.Reserva;

public class SignatureUtil {

	private static final File PRIVATE_FILE = new File("setup/keys/dsa_private.key");
	private static final File PUBLIC_FILE = new File("setup/keys/dsa_public.key");
	private static final File FIRMAS_DIR = new File("setup/firmas/reservas");

	private static PrivateKey privateKey;
	private static PublicKey publicKey;

	private static void loadPrivateKey() throws Exception {
		if (privateKey != null) {
			return;
		}
		if (!PRIVATE_FILE.exists()) {
			throw new RuntimeException(LanguageUtils.get("error.dsa.privateKey.missing"));
		}
		try (BufferedReader br = new BufferedReader(new FileReader(PRIVATE_FILE))) {
			BigInteger G = new BigInteger(br.readLine());
			BigInteger P = new BigInteger(br.readLine());
			BigInteger Q = new BigInteger(br.readLine());
			BigInteger X = new BigInteger(br.readLine());
			DSAPrivateKeySpec keySpec = new DSAPrivateKeySpec(X, P, Q, G);
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			privateKey = keyFactory.generatePrivate(keySpec);
		}
	}

	private static void loadPublicKey() throws Exception {
		if (publicKey != null) {
			return;
		}
		if (!PUBLIC_FILE.exists()) {
			throw new RuntimeException(LanguageUtils.get("error.dsa.publicKey.missing"));
		}
		try (BufferedReader br = new BufferedReader(new FileReader(PUBLIC_FILE))) {
			BigInteger Y = new BigInteger(br.readLine());
			BigInteger P = new BigInteger(br.readLine());
			BigInteger Q = new BigInteger(br.readLine());
			BigInteger G = new BigInteger(br.readLine());
			DSAPublicKeySpec keySpec = new DSAPublicKeySpec(Y, P, Q, G);
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			publicKey = keyFactory.generatePublic(keySpec);
		}
	}

	private static byte[] buildPayload(Reserva r) {
		String data = r.getReservaId() + "|" + r.getHorarioVuelo().getNumeroVuelo() + "|" + r.getVueloId() + "|"
				+ r.getAsiento() + "|" + r.getPasajero().getPasajeroId() + "|" + r.getPrecio();
		return data.getBytes(StandardCharsets.UTF_8);
	}

	public static void signReserva(Reserva r) throws Exception {
		if (r == null || r.getReservaId() == null) {
			throw new IllegalArgumentException("Reserva must have an ID before signing.");
		}
		loadPrivateKey();
		if (!FIRMAS_DIR.exists()) {
			FIRMAS_DIR.mkdirs();
		}

		byte[] payload = buildPayload(r);

		Signature sig = Signature.getInstance("SHA1withDSA");
		sig.initSign(privateKey);
		sig.update(payload);
		byte[] signatureBytes = sig.sign();

		File out = new File(FIRMAS_DIR, "reserva_" + r.getReservaId() + ".sig");
		String base64 = Base64.getEncoder().encodeToString(signatureBytes);
		Files.writeString(out.toPath(), base64, StandardCharsets.UTF_8);
	}

	public static boolean verifyReserva(Reserva r) throws Exception {
		if (r == null || r.getReservaId() == null) {
			throw new IllegalArgumentException("Reserva must have an ID to verify.");
		}
		loadPublicKey();

		File sigFile = new File(FIRMAS_DIR, "reserva_" + r.getReservaId() + ".sig");
		if (!sigFile.exists()) {
			System.out.println(LanguageUtils.get("info.reservation.signature.missing"));
			return false;
		}

		String base64 = Files.readString(sigFile.toPath(), StandardCharsets.UTF_8);
		byte[] signatureBytes = Base64.getDecoder().decode(base64);
		byte[] payload = buildPayload(r);

		Signature sig = Signature.getInstance("SHA1withDSA");
		sig.initVerify(publicKey);
		sig.update(payload);
		boolean ok = sig.verify(signatureBytes);

		if (ok) {
			System.out.println(LanguageUtils.get("info.reservation.signature.valid"));
		} else {
			System.out.println(LanguageUtils.get("info.reservation.signature.invalid"));
		}
		return ok;
	}
}
