package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class is the data validation class used mainly to make sure that the
 * data entered by the user is correct to avoid exception afterwards
 */
public class ValidationUtils {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static String readString(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				String aux = reader.readLine().trim();
				if (!aux.isEmpty())
					return aux;
				System.out.println(LanguageUtils.get("error.empty"));
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
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
				System.out.println("Error: " + e.getMessage());
			} catch (NumberFormatException e) {
				System.out.println(LanguageUtils.get("error.numberFormat"));
			}
		}

	}
}
