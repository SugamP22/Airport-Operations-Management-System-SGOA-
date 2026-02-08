package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ValidationUtils {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static String readString(String prompt) {
		while (true) {
			try {
				System.out.flush();
				System.out.print(prompt);
				String aux = reader.readLine().trim();
				if (!aux.isEmpty())
					return aux;
				System.err.println(LanguageUtils.get("error.empty"));
			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

	}

	public static int readInt(String prompt) {
		while (true) {
			try {
				System.out.flush();
				System.out.print(prompt);
				String aux = reader.readLine().trim();
				if (!aux.isEmpty()) {
					int res = Integer.parseInt(aux);
					return res;
				}
				System.err.println(LanguageUtils.get("error.empty"));
			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
			} catch (NumberFormatException e) {
				System.err.println(LanguageUtils.get("error.numberFormat"));
			}
		}

	}
}
