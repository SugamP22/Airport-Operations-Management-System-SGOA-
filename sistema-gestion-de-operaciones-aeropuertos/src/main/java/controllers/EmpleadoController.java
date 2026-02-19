package controllers;

import config.MongoDbUtil;
import authService.CurrentUser;
import utils.LanguageUtils;
import utils.MenuUtils;
import utils.ValidationUtils;

public class EmpleadoController {
	private final FlightsController FLIGHTS_CONTROLLER;
	private final WeatherController WEATHER_CONTROLLER;
	private final ReservationController RESERVATION_CONTROLLER;
	private final PassengersController PASSENGERS_CONTROLLER;

	public EmpleadoController(FlightsController FLIGHTS_CONTROLLER, WeatherController WEATHER_CONTROLLER,
			ReservationController RESERVATION_CONTROLLER, PassengersController PASSENGERS_CONTROLLER) {
		this.FLIGHTS_CONTROLLER = FLIGHTS_CONTROLLER;
		this.WEATHER_CONTROLLER = WEATHER_CONTROLLER;
		this.RESERVATION_CONTROLLER = RESERVATION_CONTROLLER;
		this.PASSENGERS_CONTROLLER = PASSENGERS_CONTROLLER;
	}

	public void openDashboard() {
		// This is the main loop I use to show the employee menu until the user exits
		System.out.println(CurrentUser.empleado.getNombre() + " " + LanguageUtils.get("user.found"));
		int option;
		do {
			System.out.println();
			MenuUtils.menuEmpleado();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switchEmpleado(option);

		} while (option != 0);
	}

	private void switchEmpleado(int option) {
		switch (option) {
		case 1 -> showMenuFlights();
		case 2 -> showMenuReserva();
		case 3 -> showMenuPasajero();
		case 4 -> {
			MongoDbUtil.getDatabase();
			try {
				showMenuWeather();
			} catch (Exception e) {
				System.out.println(LanguageUtils.get("error.general") + e.getMessage());
			} finally {
				MongoDbUtil.shutdown();
			}
		}
		case 5 -> {
			// Verify reservation signatures (DSA)
			new ReservationController().verifyReservation();
		}
		case 0 -> {
			// Volviendo de admin panel
		}
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));

		}
	}

	private void showMenuWeather() {
		int option;
		do {
			MenuUtils.menuWeatherEmpleado();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switch (option) {
			case 1 -> WEATHER_CONTROLLER.insertarNuevoClima();
			case 2 -> WEATHER_CONTROLLER.consultarPorAeropuerto();
			case 3 -> WEATHER_CONTROLLER.consultarPorRangoFecha();
			case 4 -> WEATHER_CONTROLLER.consultarPorNieblaOTormenta();
			case 0 -> { }
			default -> System.out.println(LanguageUtils.get("error.numberFormat"));
			}
		} while (option != 0);
	}

	private void showMenuFlights() {
		// Employee version of the flights menu (read-only operations)
		int option;
		do {
			MenuUtils.MenuCRUDFlightsAdmin();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switchFlights(option);
		} while (option != 0);
	}

	private void switchFlights(int option) {
		switch (option) {
		case 1 -> FLIGHTS_CONTROLLER.createFlights();
		case 2 -> FLIGHTS_CONTROLLER.modifyFlights();
		case 3 -> FLIGHTS_CONTROLLER.searchFlights();
		case 4 -> FLIGHTS_CONTROLLER.removeFlights();
		case 0 -> {
			System.out.println();
			System.out.println(LanguageUtils.get("info.adminDashBoard"));
		}
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));
		}

	}

	private void showMenuPasajero() {
		// Employee version of the passenger submenu (search / show all)
		int option;
		do {
			MenuUtils.menuPasajeroAdmin();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switchPasajero(option);
		} while (option != 0);

	}

	private void switchPasajero(int option) {
		switch (option) {
		case 1 -> PASSENGERS_CONTROLLER.createPassenger();
		case 0 -> {
			System.out.println();
			System.out.println(LanguageUtils.get("info.adminDashBoard"));
		}
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));
		}

	}

	private void showMenuReserva() {
		int option;
		do {
			MenuUtils.menuReservaAdmin();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switchReserva(option);
		} while (option != 0);
	}

	private void switchReserva(int option) {
		switch (option) {
		case 1 -> RESERVATION_CONTROLLER.searchReservation();
		case 2 -> RESERVATION_CONTROLLER.showAll();
		case 3 -> RESERVATION_CONTROLLER.calcularPrecioTotalReservas();
		case 0 -> {
			System.out.println();
			System.out.println(LanguageUtils.get("info.adminDashBoard"));
		}
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));
		}

	}
}
