package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import entities.Departamento;

/**
 * This class is the data validation class used mainly to make sure that the
 * data entered by the user is correct to avoid exception afterwards
 */
public class ValidationUtils {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static String readString(String prompt) {
		while (true) {
			String aux = "";
			try {
				System.out.print(prompt);
				aux = reader.readLine().trim();
				if (!aux.isEmpty())
					return aux;
				System.out.println(LanguageUtils.get("error.empty"));
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input"));
			}
		}

	}

	public static String readStringOpcional(String prompt) {
		while (true) {
			String aux = "";
			try {
				System.out.print(prompt);
				aux = reader.readLine().trim();
				return aux;
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input"));
			}
		}

	}

	public static Departamento readDepartmento(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				String aux = reader.readLine().trim();
				if (!aux.isEmpty()) {
					String res = aux.substring(0, 1).toUpperCase() + aux.substring(1).toLowerCase();
					Departamento d = Departamento.valueOf(res);
					return d;
				}
				System.out.println(LanguageUtils.get("error.empty"));
			} catch (Exception e) {
				System.out.println(LanguageUtils.get("error.read.input"));
			}
		}
	}

	public static int readInt(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				String aux = reader.readLine().trim();
				if (!aux.isEmpty()) {
					int res = Integer.parseInt(aux);
					return res;
				}
				System.out.println(LanguageUtils.get("error.empty"));
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input"));
			} catch (NumberFormatException e) {
				System.out.println(LanguageUtils.get("error.empty"));
			}
		}

	}

	public static char readChar(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				String aux = reader.readLine().trim();
				if (!aux.isEmpty() && aux.length() == 1) {
					char res = aux.charAt(0);
					return res;
				}
				System.out.println(LanguageUtils.get("error.oneCharacter"));
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input"));
			}
		}

	}

	public static char readSexo(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				String aux = reader.readLine().trim();
				if (!aux.isEmpty() && aux.length() == 1) {
					char res = Character.toUpperCase(aux.charAt(0));
					if (res == 'M' || res == 'F') {
						return res;
					}
					System.out.println(LanguageUtils.get("error.invalid.input"));
					continue;
				}
				System.out.println(LanguageUtils.get("error.oneCharacter"));
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input"));
			}
		}

	}

	public static Integer readInteger(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				String aux = reader.readLine().trim();
				if (!aux.isEmpty()) {
					Integer res = Integer.parseInt(aux);
					return res;
				}
				System.out.println(LanguageUtils.get("error.empty"));
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input"));
			} catch (NumberFormatException e) {
				System.out.println(LanguageUtils.get("error.empty"));
			}
		}
	}

	public static double readDouble(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				String aux = reader.readLine().trim();
				if (!aux.isEmpty()) {
					double res = Double.parseDouble(aux);
					return res;
				}
				System.out.println(LanguageUtils.get("error.empty"));
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input"));
			} catch (NumberFormatException e) {
				System.out.println(LanguageUtils.get("error.empty"));
			}
		}
	}

	public static LocalTime readLocalTime(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				String aux = reader.readLine();
				if (aux != null && !aux.trim().isEmpty()) {
					return LocalTime.parse(aux.trim());
				}
				System.out.println(LanguageUtils.get("error.empty"));
			} catch (DateTimeParseException e) {
				System.out.println(LanguageUtils.get("error.invalid.time"));
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input"));
			}
		}
	}

	public static LocalDate readLocalDate(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				String aux = reader.readLine();
				if (aux != null && !aux.trim().isEmpty()) {
					return LocalDate.parse(aux.trim());
				}
				System.out.println(LanguageUtils.get("error.empty"));
			} catch (DateTimeParseException e) {
				System.out.println(LanguageUtils.get("error.invalid.date"));
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input"));
			}
		}
	}

	public static String readEmail(String string) {
		while (true) {
			String res = readString(string);
			if (Pattern.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$", res)) {
				return res;
			}
			System.out.println(LanguageUtils.get("error.invalid.email"));
		}

	}

	public static String readTelefono(String string) {
		while (true) {
			String res = readStringOpcional(string);
			if (res.isEmpty())
				return null;
			if (Pattern.matches("^(?:(?:\\+34|0034)\\s?)?[6789]\\d{8}$", res)) {
				return res;
			}
			System.out.println(LanguageUtils.get("error.invalid.Telefono"));
		}
	}

	private static final String PASSWORD_REGEX = "^(?=\\S{12,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).*$";

	public static String readClave(String string) {
		while (true) {
			String aux = readString(string);
			if (Pattern.matches(PASSWORD_REGEX, aux)) {
				return Md5Util.hash(aux);
			}
			System.out.println(LanguageUtils.get("error.invalid.clave"));
			System.out.println(LanguageUtils.get("error.invalid.clave.hint"));
		}
	}

	/**
	 * Reads password with requirements hint and confirm step. Returns MD5 hash only
	 * when both entries match and satisfy security rules.
	 */
	public static String readClaveWithConfirm(String prompt, String confirmPrompt) {
		System.out.println(LanguageUtils.get("security.password.requirements"));
		while (true) {
			String first = readString(prompt);
			if (!Pattern.matches(PASSWORD_REGEX, first)) {
				System.out.println(LanguageUtils.get("error.invalid.clave"));
				System.out.println(LanguageUtils.get("error.invalid.clave.hint"));
				continue;
			}
			String second = readString(confirmPrompt);
			if (!first.equals(second)) {
				System.out.println(LanguageUtils.get("error.clave.mismatch"));
				continue;
			}
			return Md5Util.hash(first);
		}
	}

}
