package utils;

import java.time.LocalDate;

/**
 * Validation for weather input: allowed clima, wind direction, and value ranges.
 */
public class WeatherValidationUtil {

	private static final String[] CLIMA_VALIDOS = { "Tormenta", "Niebla", "Despejado", "Nublado", "Lluvia", "Nieve" };

	// limited ranges for realistic weather data
	private static final double TEMP_MIN = -50;
	private static final double TEMP_MAX = 50;
	private static final int HUMEDAD_MIN = 0;
	private static final int HUMEDAD_MAX = 100;
	private static final double PRESION_MIN = 900;
	private static final double PRESION_MAX = 1100;
	private static final int VIENTO_MIN = 0;
	private static final int VIENTO_MAX = 250;
	private static final LocalDate FECHA_MIN = LocalDate.of(2000, 1, 1);

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

	public static int readDireccionViento(String prompt) {
		while (true) {
			int n = ValidationUtils.readInt(prompt);
			if (n >= 0 && n <= 360) {
				return n;
			}
			System.out.println(LanguageUtils.get("error.weather.direccion.viento"));
		}
	}

	public static double readTemperatura(String prompt) {
		while (true) {
			double n = ValidationUtils.readDouble(prompt);
			if (n >= TEMP_MIN && n <= TEMP_MAX) {
				return n;
			}
			System.out.println(LanguageUtils.get("error.weather.temperatura.range"));
		}
	}

	public static int readHumedad(String prompt) {
		while (true) {
			int n = ValidationUtils.readInt(prompt);
			if (n >= HUMEDAD_MIN && n <= HUMEDAD_MAX) {
				return n;
			}
			System.out.println(LanguageUtils.get("error.weather.humedad.range"));
		}
	}

	public static double readPresionAire(String prompt) {
		while (true) {
			double n = ValidationUtils.readDouble(prompt);
			if (n >= PRESION_MIN && n <= PRESION_MAX) {
				return n;
			}
			System.out.println(LanguageUtils.get("error.weather.presion.range"));
		}
	}

	public static int readViento(String prompt) {
		while (true) {
			int n = ValidationUtils.readInt(prompt);
			if (n >= VIENTO_MIN && n <= VIENTO_MAX) {
				return n;
			}
			System.out.println(LanguageUtils.get("error.weather.viento.range"));
		}
	}

	public static LocalDate readFechaClima(String prompt) {
		LocalDate maxDate = LocalDate.now().plusYears(1);
		while (true) {
			LocalDate d = ValidationUtils.readLocalDate(prompt);
			if (!d.isBefore(FECHA_MIN) && !d.isAfter(maxDate)) {
				return d;
			}
			System.out.println(LanguageUtils.get("error.weather.fecha.range"));
		}
	}
}
