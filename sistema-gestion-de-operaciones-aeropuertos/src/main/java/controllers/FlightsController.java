package controllers;

import java.util.List;

import entities.HorarioVuelo;
import entities.Vuelo;
import repositories.VueloDAO;
import utils.BoxedMessageUtils;
import utils.LanguageUtils;
import utils.ValidationUtils;

public class FlightsController {
	private boolean empty = false;
	private String currentId = "";

	public void createFlights() {
		System.out.println();
		BoxedMessageUtils.boxWithOutEvenSpacing(LanguageUtils.get("flight.input.title"), "=");
		String numeroVuelo = ValidationUtils.readString(LanguageUtils.get("flight.input.numero"));
		BoxedMessageUtils.horizontalRow("-");
		int origen = ValidationUtils.readInt(LanguageUtils.get("flight.input.origen"));
		BoxedMessageUtils.horizontalRow("-");
		int destino = ValidationUtils.readInt(LanguageUtils.get("flight.input.destino"));
		BoxedMessageUtils.horizontalRow("-");
		int avion = ValidationUtils.readInt(LanguageUtils.get("flight.input.avion"));
		BoxedMessageUtils.horizontalRow("-");
		char letra = ValidationUtils.readChar(LanguageUtils.get("flight.input.dias"));
		BoxedMessageUtils.horizontalRow("-");
		System.out.println();

	}

	public void readALLFlights() {
		System.out.println();
		BoxedMessageUtils.boxWithOutEvenSpacing(LanguageUtils.get("flight.all.title"), "=");
		List<Vuelo> listaVuelos = VueloDAO.getAllFlights();
		if (listaVuelos.isEmpty()) {
			empty = true;
			System.out.println(LanguageUtils.get("error.noFlights"));
			return;
		}
		empty = false;
		for (Vuelo vuelo : listaVuelos) {
			System.out.println(vuelo.toString());
			System.out.println();
			BoxedMessageUtils.horizontalRow("-");
		}
	}

	public void searchFlights() {
		readALLFlights();
		if (!empty) {
			String id = ValidationUtils.readString(LanguageUtils.get("flight.input.numero"));
			Vuelo vuelo = VueloDAO.getFLightByID(id);
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			if (vuelo != null) {
				System.out.println(LanguageUtils.get("flight.found") + "\n");
				currentId = vuelo.getNumeroVuelo();
				System.out.println(vuelo.toString());
				System.out.println();
				return;
			}
			currentId = "";
			System.out.println(LanguageUtils.get("error.flight.existence"));
		}

	}

	public void modifyFlights() {
		searchFlights();
		if (!currentId.isEmpty()) {
			int origen = ValidationUtils.readInt(LanguageUtils.get("flight.input.origen"));
			BoxedMessageUtils.horizontalRow("-");
			int destino = ValidationUtils.readInt(LanguageUtils.get("flight.input.destino"));
			BoxedMessageUtils.horizontalRow("-");
			int avion = ValidationUtils.readInt(LanguageUtils.get("flight.input.avion"));
			BoxedMessageUtils.horizontalRow("-");
			char letra = ValidationUtils.readChar(LanguageUtils.get("flight.input.dias"));
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
		}

	}

	public void removeFlights() {
		searchFlights();
		if (!currentId.isEmpty()) {
			char letra = ValidationUtils.readChar(LanguageUtils.get("flight.delete.confirm"));
			if (Character.toUpperCase(letra) == 'Y') {
				try {
					VueloDAO.deleteByID(currentId);
					System.out.println(LanguageUtils.get("flight.delete.success"));
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					System.out.println(LanguageUtils.get("flight.delete.error"));
				}

			} else {
				System.out.println(LanguageUtils.get("flight.delete.cancel"));
			}
			currentId = "";
		}
	}

	public void showSchedules() {
		readALLFlights();
		if (!empty) {
			String id = ValidationUtils.readString(LanguageUtils.get("flight.input.numero"));
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			if (!id.isEmpty()) {
				try {
					HorarioVuelo h = VueloDAO.getFlightSchedules(id);
					System.out.println(LanguageUtils.get("flight.found") + "\n");
					System.out.println(h.toString());
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}

			}
		}
	}

}
