package controllers;

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
		case 4 -> System.out.println(LanguageUtils.get("info.module.weather.pending"));
		case 5 -> System.out.println(LanguageUtils.get("info.module.security.dsa.pending"));
		case 0 -> {
			// normal exit from employee dashboard, no shutting down text
		}
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));

		}
	}

	private void showMenuPasajero() {
		int option;
		do {
			MenuUtils.menuPasajeroEmpleado();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switchPasajero(option);
		} while (option != 0);

	}

	private void switchPasajero(int option) {
		switch (option) {
		case 1 -> PASSENGERS_CONTROLLER.showall();
		case 2 -> PASSENGERS_CONTROLLER.searchPassenger();
		case 0 -> {
			System.out.println();
			System.out.println(LanguageUtils.get("info.employeeDashBoard"));
		}
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));
		}

	}

	private void showMenuReserva() {
		int option;
		do {
			MenuUtils.menuReservaEmpleado();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switchReserva(option);
		} while (option != 0);
	}

	private void switchReserva(int option) {
		switch (option) {
		case 1 -> RESERVATION_CONTROLLER.searchReservation();
		case 2 -> RESERVATION_CONTROLLER.showAll();
		case 0 -> {
			System.out.println();
			System.out.println(LanguageUtils.get("info.employeeDashBoard"));
		}
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
