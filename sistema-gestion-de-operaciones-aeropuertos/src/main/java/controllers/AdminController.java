package controllers;

import authService.CurrentUser;
import utils.LanguageUtils;
import utils.MenuUtils;
import utils.ValidationUtils;

public class AdminController {
	private final FlightsController FLIGHTS_CONTROLLER;
	private final WeatherController WEATHER_CONTROLLER;
	private final ReservationController RESERVATION_CONTROLLER;
	private final PassengersController PASSENGERS_CONTROLLER;
	private final EmployeeCRUDController employeeCRUDController;

	public AdminController(FlightsController FLIGHTS_CONTROLLER, WeatherController WEATHER_CONTROLLER,
			ReservationController RESERVATION_CONTROLLER, PassengersController PASSENGERS_CONTROLLER) {
		this.FLIGHTS_CONTROLLER = FLIGHTS_CONTROLLER;
		this.WEATHER_CONTROLLER = WEATHER_CONTROLLER;
		this.RESERVATION_CONTROLLER = RESERVATION_CONTROLLER;
		this.PASSENGERS_CONTROLLER = PASSENGERS_CONTROLLER;
		employeeCRUDController = new EmployeeCRUDController();
	}

	public void openDashboard() {
		System.out.println(CurrentUser.empleado.getNombre() + " " + LanguageUtils.get("user.found"));
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
		case 2 -> showMenuReserva();
		case 3 -> showMenuPasajero();
		case 4 -> showMenuEmployee();
		case 5 -> System.out.println(LanguageUtils.get("info.module.weather.pending"));
		case 6 -> System.out.println(LanguageUtils.get("info.module.security.des.pending"));
		case 7 -> System.out.println(LanguageUtils.get("info.module.security.dsa.pending"));
		case 0 -> System.out.println(LanguageUtils.get("system.closing"));
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));

		}
	}

	private void showMenuEmployee() {
		int option;
		do {
			MenuUtils.MenuCRUDEmployeeAdmin();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switchEmployeeCRUD(option);
		} while (option != 0);
	}

	private void switchEmployeeCRUD(int option) {
		switch (option) {
		case 1 -> employeeCRUDController.createEmployee();
		case 2 -> employeeCRUDController.modifyEmployee();
		case 3 -> employeeCRUDController.searchEmployee();
		case 4 -> employeeCRUDController.removeEmployee();
		case 0 -> {
			System.out.println();
			System.out.println(LanguageUtils.get("info.adminDashBoard"));
		}
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

	private void showMenuPasajero() {
		int option;
		do {
			MenuUtils.menuPasajeroAdmin();
			option = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			switchPasajero(option);
		} while (option != 0);

	}

	private void switchPasajero(int option) {
		switch (option) {
		case 3 -> PASSENGERS_CONTROLLER.createPassenger();
		case 2 -> PASSENGERS_CONTROLLER.searchPassenger();
		case 1 -> PASSENGERS_CONTROLLER.showall();
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
		case 1 -> RESERVATION_CONTROLLER.createReservation();
		case 2 -> RESERVATION_CONTROLLER.searchReservation();
		case 3 -> RESERVATION_CONTROLLER.showAll();
		case 0 -> {
			System.out.println();
			System.out.println(LanguageUtils.get("info.adminDashBoard"));
		}
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));
		}

	}

}
