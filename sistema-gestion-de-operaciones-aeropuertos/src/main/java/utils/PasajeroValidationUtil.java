package utils;

/**
 * Utility class I use to validate and encrypt passenger-specific fields (passport,
 * email, phone) before saving them.
 */

import java.util.regex.Pattern;

public class PasajeroValidationUtil {

	public static String readPassport(String string) {
		while (true) {
			try {
				String aux = ValidationUtils.readString(string);
				if (Pattern.matches("^[A-Za-z0-9]{8,9}$", aux)) {
					String res = DesUtil.encrypt(aux);
					return res;
				} else {
					System.out.println(LanguageUtils.get("error.passport.invalid"));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public static String readEmail(String string) {
		while (true) {
			try {
				String aux = ValidationUtils.readEmail(string);
				String res = DesUtil.encrypt(aux);
				return res;

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static String readTelefono(String string) {
		while (true) {
			try {
				String aux = ValidationUtils.readTelefono(string);
				String res = DesUtil.encrypt(aux);
				return res;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static int readCodigoPostal(String string) {
		while (true) {
			try {
				int res = ValidationUtils.readInt(string);
				if (Pattern.matches("^\\d{5}$", String.valueOf(res))) {
					return res;
				} else {
					System.out.println(LanguageUtils.get("error.postal.invalid"));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
