package controllers;

/**
 * Controller I use to manage all flight operations (create, update, search, delete and view schedules)
 * through the console menus.
 */

import java.util.List;

import entities.Aeropuertos;
import entities.Avion;
import entities.HorarioVuelo;
import entities.Vuelo;
import repositories.AvionDAO;
import repositories.VueloDAO;
import utils.BoxedMessageUtils;
import utils.DayUtils;
import utils.LanguageUtils;
import utils.MenuUtils;
import utils.ValidationUtils;
import utils.VueloValidationUtil;

public class FlightsController {
	private boolean empty = false;
	private String currentId = "";

	public void createFlights() {
		try {
			Vuelo vuelo = new Vuelo();
			System.out.println();
			BoxedMessageUtils.boxWithOutEvenSpacing(LanguageUtils.get("flight.input.title"), "=");
			System.out.println();

			// Here I validate that the flight number is between 3 and 4 digits (only
			// numbers)
			String numeroVuelo;
			while (true) {
				numeroVuelo = ValidationUtils.readString(LanguageUtils.get("flight.input.numero"));
				if (numeroVuelo.matches("^[0-9]{3,4}$")) {
					break;
				}
				System.out.println(LanguageUtils.get("error.invalid.flightNumber"));
			}
			Vuelo existing = VueloDAO.getFLightByID(numeroVuelo);
			if (existing != null) {
				throw new IllegalArgumentException(LanguageUtils.get("error.flight.existence"));
			}
			vuelo.setNumeroVuelo(numeroVuelo);
			BoxedMessageUtils.horizontalRow("-");

			Aeropuertos aeropuertosOrigen = VueloValidationUtil.readOrigin(LanguageUtils.get("flight.input.origen"));
			vuelo.setOrigen(aeropuertosOrigen);
			BoxedMessageUtils.horizontalRow("-");

			Aeropuertos destino = VueloValidationUtil.readDestino(LanguageUtils.get("flight.input.destino"),
					aeropuertosOrigen.getAeropuertoId());
			vuelo.setDestino(destino);
			BoxedMessageUtils.horizontalRow("-");

			AvionDAO.readAll();
			Avion avion = VueloValidationUtil.readAvion(LanguageUtils.get("flight.input.avion"));
			vuelo.setAvion(avion);
			BoxedMessageUtils.horizontalRow("-");

			DayUtils.selectDays(vuelo);
			BoxedMessageUtils.horizontalRow("-");

			HorarioVuelo horarioVuelo = new HorarioVuelo();
			horarioVuelo.setSalida(ValidationUtils.readLocalTime(LanguageUtils.get("flight.input.salida")));
			horarioVuelo.setLlegada(ValidationUtils.readLocalTime(LanguageUtils.get("flight.input.llegada")));
			horarioVuelo.setVuelo(vuelo);
			horarioVuelo.setNumeroVuelo(vuelo.getNumeroVuelo());
			vuelo.setHorarioVuelo(horarioVuelo);
			BoxedMessageUtils.horizontalRow("-");

			VueloDAO.createFlight(vuelo);
			System.out.println(LanguageUtils.get("flight.create.success"));
			System.out.println(vuelo.toString());
			System.out.println();

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void readALLFlights() {
		// I use this to print all flights in a simple list for the other operations
		System.out.println();
		BoxedMessageUtils.boxWithOutEvenSpacing(LanguageUtils.get("flight.all.title"), "=");
		System.out.println();
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
		int option;
		do {
			MenuUtils.menuFlightFilters();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switch (option) {
			case 1:
				searchByFlightNumber();
				break;
			case 2:
				listByAirline();
				break;
			case 3:
				listByOperatingDay();
				break;
			case 4:
				listByDestination();
				break;
			case 5:
				listByOrigin();
				break;
			case 6:
				listByDayAndDestination();
				break;
			case 0:
				break;
			default:
				System.out.println(LanguageUtils.get("error.invalidOption"));
				break;
			}
		} while (option != 0);
	}

	private void searchByFlightNumber() {
		readALLFlights();
		if (!empty) {
			String id = ValidationUtils.readString(LanguageUtils.get("flight.search.numero"));
			Vuelo vuelo = VueloDAO.getFLightByID(id);
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			if (vuelo != null) {
				System.out.println(LanguageUtils.get("flight.found") + "\n");
				System.out.println(vuelo.toString());
				System.out.println();
				return;
			}
			System.out.println(LanguageUtils.get("error.flight.existence"));
		}
	}

	private void listByAirline() {
		String airline = ValidationUtils.readStringOpcional(LanguageUtils.get("flight.search.airline"));
		List<Vuelo> vuelos;
		if (airline == null || airline.trim().isEmpty()) {
			vuelos = VueloDAO.getFlightsWithoutAirplane();
		} else {
			vuelos = VueloDAO.getFlightsByAirline(airline);
		}
		printFlights(vuelos);
	}

	private void listByOperatingDay() {
		String day = ValidationUtils.readString(LanguageUtils.get("flight.search.day"));
		try {
			List<Vuelo> vuelos = VueloDAO.getFlightsByOperatingDay(day);
			printFlights(vuelos);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	private void listByDestination() {
		if (!printFlightOriginDestinationSummary()) {
			return;
		}
		String destination = ValidationUtils.readString(LanguageUtils.get("flight.search.destination"));
		List<Vuelo> vuelos = VueloDAO.getFlightsByDestination(destination);
		printFlights(vuelos);
	}

	private void listByOrigin() {
		if (!printFlightOriginDestinationSummary()) {
			return;
		}
		String origin = ValidationUtils.readString(LanguageUtils.get("flight.search.origin"));
		List<Vuelo> vuelos = VueloDAO.getFlightsByOrigin(origin);
		printFlights(vuelos);
	}

	private void listByDayAndDestination() {
		if (!printFlightOriginDestinationSummary()) {
			return;
		}
		String day = ValidationUtils.readString(LanguageUtils.get("flight.search.day"));
		String destination = ValidationUtils.readString(LanguageUtils.get("flight.search.destination"));
		try {
			List<Vuelo> vuelos = VueloDAO.getFlightsByDayAndDestination(day, destination);
			printFlights(vuelos);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	private void printFlights(List<Vuelo> vuelos) {
		BoxedMessageUtils.horizontalRow("-");
		System.out.println();
		if (vuelos == null || vuelos.isEmpty()) {
			System.out.println(LanguageUtils.get("error.noFilteredFlights"));
			return;
		}
		System.out.println(LanguageUtils.get("flight.filtered.title"));
		System.out.println();
		for (Vuelo vuelo : vuelos) {
			System.out.println(vuelo.toString());
			System.out.println();
			BoxedMessageUtils.horizontalRow("-");
		}
	}

	private boolean printFlightOriginDestinationSummary() {
		List<Vuelo> vuelos = VueloDAO.getAllFlights();
		if (vuelos == null || vuelos.isEmpty()) {
			System.out.println(LanguageUtils.get("error.noFlights"));
			return false;
		}
		BoxedMessageUtils.horizontalRow("-");
		System.out.println();
		for (Vuelo v : vuelos) {
			String numero = v.getNumeroVuelo();
			String origenNombre = v.getOrigen() != null ? v.getOrigen().getNombre() : "N/A";
			String origenIata = v.getOrigen() != null ? v.getOrigen().getIata() : "N/A";
			String origenIcao = v.getOrigen() != null ? v.getOrigen().getIcao() : "N/A";

			String destinoNombre = v.getDestino() != null ? v.getDestino().getNombre() : "N/A";
			String destinoIata = v.getDestino() != null ? v.getDestino().getIata() : "N/A";
			String destinoIcao = v.getDestino() != null ? v.getDestino().getIcao() : "N/A";

			System.out.println("Flight: " + numero + " | Origin: " + origenNombre + " (" + origenIata + "/" + origenIcao
					+ ")" + " | Destination: " + destinoNombre + " (" + destinoIata + "/" + destinoIcao + ")");
			BoxedMessageUtils.horizontalRow("-");
		}
		System.out.println();
		return true;
	}

	public void modifyFlights() {
		readALLFlights();
		if (!empty) {
			String id = ValidationUtils.readString(LanguageUtils.get("flight.modify.numero"));
			Vuelo vuelo = VueloDAO.getFLightByID(id);
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			if (vuelo != null) {
				System.out.println(LanguageUtils.get("flight.found") + "\n");
				currentId = vuelo.getNumeroVuelo();
				System.out.println(vuelo.toString());
				System.out.println();
			} else {
				currentId = "";
				System.out.println(LanguageUtils.get("error.flight.existence"));
			}
		}
		if (!currentId.isEmpty()) {
			try {
				Vuelo vuelo = VueloDAO.getFLightByID(currentId);
				if (vuelo == null) {
					System.out.println(LanguageUtils.get("error.flight.existence"));
					currentId = "";
					return;
				}

				Aeropuertos origen = VueloValidationUtil.readOrigin(LanguageUtils.get("flight.input.origen"));
				vuelo.setOrigen(origen);
				BoxedMessageUtils.horizontalRow("-");

				Aeropuertos destino = VueloValidationUtil.readDestino(LanguageUtils.get("flight.input.destino"),
						origen.getAeropuertoId());
				vuelo.setDestino(destino);
				BoxedMessageUtils.horizontalRow("-");

				Avion avion = VueloValidationUtil.readAvion(LanguageUtils.get("flight.input.avion"));
				// On update: empty input will clear the assigned plane (set to null)
				vuelo.setAvion(avion);
				BoxedMessageUtils.horizontalRow("-");

				DayUtils.selectDays(vuelo);
				BoxedMessageUtils.horizontalRow("-");

				HorarioVuelo horarioVuelo = vuelo.getHorarioVuelo();
				if (horarioVuelo == null) {
					horarioVuelo = new HorarioVuelo();
				}
				horarioVuelo.setSalida(ValidationUtils.readLocalTime(LanguageUtils.get("flight.input.salida")));
				horarioVuelo.setLlegada(ValidationUtils.readLocalTime(LanguageUtils.get("flight.input.llegada")));
				horarioVuelo.setVuelo(vuelo);
				horarioVuelo.setNumeroVuelo(vuelo.getNumeroVuelo());
				vuelo.setHorarioVuelo(horarioVuelo);
				BoxedMessageUtils.horizontalRow("-");

				VueloDAO.updateFlight(vuelo);
				System.out.println(LanguageUtils.get("flight.updated"));
				System.out.println(vuelo.toString());
				BoxedMessageUtils.horizontalRow("-");
				System.out.println();
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				currentId = "";
			}
		}

	}

	public void removeFlights() {
		readALLFlights();
		if (!empty) {
			String id = ValidationUtils.readString(LanguageUtils.get("flight.delete.numero"));
			Vuelo vuelo = VueloDAO.getFLightByID(id);
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			if (vuelo != null) {
				System.out.println(LanguageUtils.get("flight.found") + "\n");
				currentId = vuelo.getNumeroVuelo();
				System.out.println(vuelo.toString());
				System.out.println();
			} else {
				currentId = "";
				System.out.println(LanguageUtils.get("error.flight.existence"));
			}
		}
		if (!currentId.isEmpty()) {
			char letra = ValidationUtils.readChar(LanguageUtils.get("flight.delete.confirm"));
			char upper = Character.toUpperCase(letra);
			// Accept 'S' (Si) and 'Y' (Yes) as confirmation
			if (upper == 'S' || upper == 'Y') {
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
			String id = ValidationUtils.readString(LanguageUtils.get("flight.input.schedules"));
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			if (!id.isEmpty()) {
				try {
					HorarioVuelo h = VueloDAO.getFlightSchedules(id);
					if (h == null) {
						System.out.println(LanguageUtils.get("error.noSchedules"));
						return;
					}
					System.out.println(LanguageUtils.get("flight.found") + "\n");
					System.out.println(h.toString());
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}

			}
		}
	}

}
