package controllers;

import utils.LanguageUtils;
import utils.MenuUtils;
import utils.ValidationUtils;

public class AdminController {
	private final FlightsController FLIGHTS_CONTROLLER;
	private final WeatherController WEATHER_CONTROLLER;
	private final ReservationController RESERVATION_CONTROLLER;
	private final PassengersController PASSENGERS_CONTROLLER;

	public AdminController(FlightsController FLIGHTS_CONTROLLER, WeatherController WEATHER_CONTROLLER,
			ReservationController RESERVATION_CONTROLLER) {
		this.FLIGHTS_CONTROLLER = FLIGHTS_CONTROLLER;
		this.WEATHER_CONTROLLER = WEATHER_CONTROLLER;
		this.RESERVATION_CONTROLLER = RESERVATION_CONTROLLER;
		PASSENGERS_CONTROLLER = new PassengersController();
	}

	public void openDashboard() {
		System.out.println(LoginController.getUser().getEmpleado().getNombre() + " " + LanguageUtils.get("user.found"));
		int option;
		do {
			System.out.println();
			MenuUtils.menuAdmin();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switchEmpleado(option);

		} while (option != 0);
	}

	private void switchEmpleado(int option) {
		switch (option) {
		case 1 -> showMenuFlights();
		case 2 -> System.out.println("OPTION 2");
		case 3 -> System.out.println("OPTION 3");
		case 4 -> System.out.println("OPTION 3");
		case 5 -> System.out.println("OPTION 3");
		case 6 -> System.out.println("OPTION 3");
		case 0 -> System.out.println("OPTION 0");
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));

		}
	}

	private void showMenuFlights() {
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

}
