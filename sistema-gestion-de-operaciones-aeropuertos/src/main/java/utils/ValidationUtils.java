package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import entities.Aeropuertos;
import entities.Avion;
import repositories.AeropuertosDAO;
import repositories.AvionDAO;

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
				System.out.println("Error: " + e.getMessage());
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
				System.out.println("Error: " + e.getMessage());
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

	public static Aeropuertos readOrigin(String prompt) {
		while (true) {
			AeropuertosDAO.readAll();
			System.out.print(prompt);

			try {
				String aux = reader.readLine();
				if (aux == null || aux.trim().isEmpty()) {
					System.out.println(LanguageUtils.get("error.empty"));
					continue;
				}

				Integer id = Integer.parseInt(aux.trim());
				Aeropuertos aeropuerto = AeropuertosDAO.getById(id);

				if (aeropuerto != null) {
					return aeropuerto;
				}

				System.out.println(LanguageUtils.get("error.invalid.aeropuertoID"));
			} catch (NumberFormatException e) {
				System.out.println(LanguageUtils.get("error.invalid.aeropuertoID"));
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input")); // if you have this key
			}
		}
	}

	public static Aeropuertos readDestino(String prompt, Integer aeropuertoId) {
		while (true) {
			AeropuertosDAO.readAll();
			System.out.print(prompt);
			try {
				String aux = reader.readLine();
				if (aux == null || aux.trim().isEmpty()) {
					System.out.println(LanguageUtils.get("error.empty"));
					continue;
				}
				Integer id = Integer.parseInt(aux.trim());
				Aeropuertos destino = AeropuertosDAO.getById(id);
				if (destino == null) {
					System.out.println(LanguageUtils.get("error.invalid.aeropuertoID"));
					continue;
				}
				if (destino.getAeropuertoId().equals(aeropuertoId)) {
					System.out.println(LanguageUtils.get("error.invailid.destino"));
					continue;
				}
				return destino;
			} catch (NumberFormatException e) {
				System.out.println(LanguageUtils.get("error.invalid.aeropuertoID"));
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input"));
			}
		}
	}

	public static Avion readAvion(String prompt) {
		while (true) {
			AvionDAO.readAll();
			System.out.print(prompt);

			try {
				String aux = reader.readLine();
				if (aux == null || aux.trim().isEmpty()) {
					return null;
				}

				Integer id = Integer.parseInt(aux.trim());
				Avion avion = AvionDAO.getById(id);

				if (avion != null) {
					return avion;
				}

				System.out.println(LanguageUtils.get("error.invalid.avionID"));
			} catch (NumberFormatException e) {
				System.out.println(LanguageUtils.get("error.invalid.avionID"));
			} catch (IOException e) {
				System.out.println(LanguageUtils.get("error.read.input")); // if you have this key
			}
		}
	}
}
