package controllers;

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

			String numeroVuelo = ValidationUtils.readString(LanguageUtils.get("flight.input.numero"));
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
			System.out.println(LanguageUtils.get("flight.found"));
			System.out.println(vuelo.toString());
			System.out.println();

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void readALLFlights() {
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
		readALLFlights();
		if (!empty) {
			String id = ValidationUtils.readString(LanguageUtils.get("flight.search.numero"));
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
				if (avion != null) {
					vuelo.setAvion(avion);
				}
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
			if (Character.toUpperCase(letra) == 'S') {
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
