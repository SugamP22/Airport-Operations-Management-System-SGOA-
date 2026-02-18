package utils;

/**
 * Helper I use to validate and read airport and airplane data from the console
 * when I am creating or updating flights.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import entities.Aeropuertos;
import entities.Avion;
import repositories.AeropuertosDAO;
import repositories.AvionDAO;

public class VueloValidationUtil {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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
				System.out.println(LanguageUtils.get("error.read.input"));
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
				System.out.println(LanguageUtils.get("error.read.input"));
			}
		}
	}
}
