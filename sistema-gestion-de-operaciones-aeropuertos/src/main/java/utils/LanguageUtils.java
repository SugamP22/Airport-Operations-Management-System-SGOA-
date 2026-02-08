package utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageUtils {
	// this holds the current bundle (The loaded .properties file)
	private static ResourceBundle bundle;

	public static void setLanguage(String languageCode) {
		Locale locale = Locale.forLanguageTag(languageCode);
		// message referes to the base name of the properties file i.e
		// message_languageCode
		bundle = ResourceBundle.getBundle("message", locale);
	}

	public static String get(String key) {
		if (bundle == null) {
			setLanguage("es");
		}
		return bundle.getString(key);
	}

}
