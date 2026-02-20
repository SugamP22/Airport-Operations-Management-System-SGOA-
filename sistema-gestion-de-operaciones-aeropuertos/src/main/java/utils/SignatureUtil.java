package utils;

/**
 * Utility class that loads both public and private key from the setup folder and then sign or verify reserva
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
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
	private static final File SUMMERY_BASE = new File("setup/summery");

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

	private static String buildPayloadString(Reserva r) {
		String passenger = r.getPasajero() != null ? String.valueOf(r.getPasajero().getPasajeroId()) : "";
		String flight = r.getHorarioVuelo() != null ? r.getHorarioVuelo().getNumeroVuelo() + "|" + r.getVueloId()
				: String.valueOf(r.getVueloId());
		String price = String.valueOf(r.getPrecio());
		String date = (r.getHorarioVuelo() != null && r.getHorarioVuelo().getSalida() != null)
				? r.getHorarioVuelo().getSalida().toString()
				: "";
		return r.getReservaId() + "|" + passenger + "|" + flight + "|" + price + "|" + date;
	}

	private static byte[] buildPayload(Reserva r) {
		return buildPayloadString(r).getBytes(StandardCharsets.UTF_8);
	}

	public static String signReserva(Reserva r) throws Exception {
		if (r == null || r.getReservaId() == null) {
			throw new IllegalArgumentException(LanguageUtils.get("error.reserva.id.required.sign"));
		}
		loadPrivateKey();
		if (!FIRMAS_DIR.exists()) {
			FIRMAS_DIR.mkdirs();
		}

		byte[] payload = buildPayload(r);

		Signature sig = Signature.getInstance("DSA");
		sig.initSign(privateKey);
		sig.update(payload);
		byte[] signatureBytes = sig.sign();

		String base64 = Base64.getEncoder().encodeToString(signatureBytes);
		File out = new File(FIRMAS_DIR, "reserva_" + r.getReservaId() + ".sig");
		Files.writeString(out.toPath(), base64);

		File summeryFolder = new File(SUMMERY_BASE, "reserva_" + r.getReservaId());
		summeryFolder.mkdirs();
		Files.writeString(new File(summeryFolder, "resumen.txt").toPath(), buildPayloadString(r),
				StandardCharsets.UTF_8);
		try (OutputStream fos = Files.newOutputStream(new File(summeryFolder, "firma").toPath())) {
			fos.write(signatureBytes);
		}

		return base64;
	}

	public static boolean verifyReserva(Reserva r) throws Exception {
		if (r == null || r.getReservaId() == null) {
			throw new IllegalArgumentException(LanguageUtils.get("error.reserva.id.required.verify"));
		}
		loadPublicKey();

		byte[] signatureBytes = null;
		File sigFile = new File(FIRMAS_DIR, "reserva_" + r.getReservaId() + ".sig");
		if (sigFile.exists()) {
			String base64 = Files.readString(sigFile.toPath());
			signatureBytes = Base64.getDecoder().decode(base64);
		}
		if (signatureBytes == null) {
			File summeryFirma = new File(SUMMERY_BASE, "reserva_" + r.getReservaId() + "/firma");
			if (summeryFirma.exists()) {
				signatureBytes = Files.readAllBytes(summeryFirma.toPath());
			}
		}
		if (signatureBytes == null) {
			System.out.println(LanguageUtils.get("info.reservation.signature.missing"));
			return false;
		}

		// Build the payload to verify against.
		// Priority: use the resumen.txt file (so if someone edits that file, the
		// signature becomes invalid).
		// Fallback: rebuild from the current Reserva data.
		byte[] payload;
		File resumenFile = new File(SUMMERY_BASE, "reserva_" + r.getReservaId() + "/resumen.txt");
		if (resumenFile.exists()) {
			payload = Files.readAllBytes(resumenFile.toPath());
		} else {
			payload = buildPayload(r);
		}

		Signature sig = Signature.getInstance("DSA");
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
