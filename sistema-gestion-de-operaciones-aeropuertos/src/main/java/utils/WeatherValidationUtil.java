package utils;

/**
 * Validation for weather input: allowed clima values and wind direction 0–360.
 */
public class WeatherValidationUtil {

	// allowed values for clima (per document)
	private static final String[] CLIMA_VALIDOS = { "Tormenta", "Niebla", "Despejado", "Nublado", "Lluvia", "Nieve" };

	public static String readClima(String prompt) {
		while (true) {
			String value = ValidationUtils.readString(prompt);
			for (String c : CLIMA_VALIDOS) {
				if (c.equalsIgnoreCase(value)) {
					return c;
				}
			}
			System.out.println(LanguageUtils.get("error.weather.clima.invalid"));
		}
	}

	// wind direction 0–360 degrees
	public static int readDireccionViento(String prompt) {
		while (true) {
			int n = ValidationUtils.readInt(prompt);
			if (n >= 0 && n <= 360) {
				return n;
			}
			System.out.println(LanguageUtils.get("error.weather.direccion.viento"));
		}
	}
}
