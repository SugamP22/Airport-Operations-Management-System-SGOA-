package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	public static String hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = md.digest(input.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : buffer) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(LanguageUtils.get("error.md5NotFound"), e);
		}
	}
}