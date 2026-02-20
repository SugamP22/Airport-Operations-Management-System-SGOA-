package utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utility Class that i use to select language and get the required text
 */
public class LanguageUtils {
	// this holds the current bundle (The loaded .properties file)
	private static ResourceBundle bundle;

	/**
	 * To change the language using the second half of the file name
	 * 
	 * @param languageCode
	 */
	public static void setLanguage(String languageCode) {
		Locale locale = Locale.forLanguageTag(languageCode);
		// message referes to the base name of the properties file i.e
		// message_languageCode
		bundle = ResourceBundle.getBundle("language.message", locale);
	}

	/**
	 * TO get the text from the properties file using bundle resource
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		if (bundle == null) {
			setLanguage("es");
		}
		return bundle.getString(key);
	}

}
