package controllers;

import utils.LanguageUtils;
import utils.MenuUtils;
import utils.ValidationUtils;

public class EmpleadoController {
	private final FlightsController FLIGHTS_CONTROLLER;
	private final WeatherController WEATHER_CONTROLLER;
	private final ReservationController RESERVATION_CONTROLLER;

	public EmpleadoController(FlightsController FLIGHTS_CONTROLLER, WeatherController WEATHER_CONTROLLER,
			ReservationController RESERVATION_CONTROLLER) {
		this.FLIGHTS_CONTROLLER = FLIGHTS_CONTROLLER;
		this.WEATHER_CONTROLLER = WEATHER_CONTROLLER;
		this.RESERVATION_CONTROLLER = RESERVATION_CONTROLLER;
	}

	public void openDashboard() {
		System.out.println(LoginController.getUser().getEmpleado().getNombre() + " " + LanguageUtils.get("user.found"));
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
		case 2 -> System.out.println("OPTION 2");
		case 3 -> System.out.println("OPTION 3");
		case 0 -> System.out.println("OPTION 4");
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));

		}
	}

	private void showMenuFlights() {
		int option;
		do {
			MenuUtils.MenuCRUDFlightsEmpleado();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switchFlights(option);
		} while (option != 0);
	}

	private void switchFlights(int option) {
		switch (option) {
		case 1 -> FLIGHTS_CONTROLLER.searchFlights();
		case 2 -> FLIGHTS_CONTROLLER.showSchedules();
		case 0 -> {
			System.out.println();
			System.out.println(LanguageUtils.get("info.employeeDashBoard"));
		}
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));
		}
	}

}
