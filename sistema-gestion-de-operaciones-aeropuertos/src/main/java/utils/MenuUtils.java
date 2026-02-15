package utils;

public class MenuUtils {
	/**
	 * login title
	 */
	public static void signInTitle() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.login"), "=");
		System.out.println();
	}

	/**
	 * options in loginMenu
	 */
	public static void loginMenu() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.header"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("ui.login.option"));
		System.out.println("0. " + LanguageUtils.get("ui.exit.option"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");
	}

	/**
	 * options in langaugeMenu
	 */
	public static void languageMenu() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.lang.menu.title"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("ui.lang.option.en"));
		System.out.println("2. " + LanguageUtils.get("ui.lang.option.es"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

	/**
	 * options in menuEmpleado
	 */
	public static void menuEmpleado() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.title.employee"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("option.empleado.consultarVuelos"));
		System.out.println("2. " + LanguageUtils.get("option.empleado.reserva"));
		System.out.println("3. " + LanguageUtils.get("option.empleado.pasajero"));
		System.out.println("4. " + LanguageUtils.get("option.empleado.consultarClima"));
		System.out.println("5. " + LanguageUtils.get("option.empleado.verificarDSA"));
		System.out.println("0. " + LanguageUtils.get("option.salir"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

	/**
	 * options in menuAdmin
	 */
	public static void menuAdmin() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.title.administrator"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("option.admin.vuelos"));
		System.out.println("2. " + LanguageUtils.get("option.admin.reservas"));
		System.out.println("3. " + LanguageUtils.get("option.admin.pasajeros"));
		System.out.println("4. " + LanguageUtils.get("option.admin.empelados"));
		System.out.println("5. " + LanguageUtils.get("option.admin.meterologicos"));
		System.out.println("6. " + LanguageUtils.get("option.admin.descifrar"));
		System.out.println("7. " + LanguageUtils.get("option.admin.verificar"));
		System.out.println("0. " + LanguageUtils.get("option.salir"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

	public static void MenuCRUDFlightsAdmin() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.title.flights"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("option.flight.create"));
		System.out.println("2. " + LanguageUtils.get("option.flight.update"));
		System.out.println("3. " + LanguageUtils.get("option.flight.searchList"));
		System.out.println("4. " + LanguageUtils.get("option.flight.remove"));
		System.out.println("0. " + LanguageUtils.get("option.back.admin"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");
	}

	public static void MenuCRUDFlightsEmpleado() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.title.flights"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("option.flight.searchList"));
		System.out.println("2. " + LanguageUtils.get("option.flight.viewSchedules"));
		System.out.println("0. " + LanguageUtils.get("option.back.employee"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

	public static void menuFlightFilters() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.title.flightFilters"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("option.flight.searchByNumber"));
		System.out.println("2. " + LanguageUtils.get("option.flight.filterByAirline"));
		System.out.println("3. " + LanguageUtils.get("option.flight.filterByDay"));
		System.out.println("4. " + LanguageUtils.get("option.flight.filterByDestination"));
		System.out.println("5. " + LanguageUtils.get("option.flight.filterByDayDestination"));
		System.out.println("6. " + LanguageUtils.get("option.flight.viewSchedules"));
		System.out.println("0. " + LanguageUtils.get("option.back.flights"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");
	}

	public static void MenuCRUDEmployeeAdmin() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.titlt.CRUD.employee"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("option.employee.create"));
		System.out.println("2. " + LanguageUtils.get("option.employee.update"));
		System.out.println("3. " + LanguageUtils.get("option.employee.search"));
		System.out.println("4. " + LanguageUtils.get("option.employee.remove"));
		System.out.println("0. " + LanguageUtils.get("option.back.admin"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

	public static void menuPasajeroEmpleado() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.title.passengers"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("option.passenger.showAll"));
		System.out.println("2. " + LanguageUtils.get("option.passenger.search"));
		System.out.println("0. " + LanguageUtils.get("option.back.employee"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

	public static void menuReservaEmpleado() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.title.reservations"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("option.reservation.search"));
		System.out.println("2. " + LanguageUtils.get("option.reservation.showAll"));
		System.out.println("0. " + LanguageUtils.get("option.back.employee"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

	public static void menuReservaAdmin() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.title.reservations"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("option.reservation.create"));
		System.out.println("2. " + LanguageUtils.get("option.reservation.search"));
		System.out.println("3. " + LanguageUtils.get("option.reservation.showAll"));
		System.out.println("0. " + LanguageUtils.get("option.back.admin"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

	public static void menuPasajeroAdmin() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.title.passengers"), "=");
		System.out.println();
		System.out.println("1. " + LanguageUtils.get("option.passenger.showAll"));
		System.out.println("2. " + LanguageUtils.get("option.passenger.search"));
		System.out.println("3. " + LanguageUtils.get("option.passenger.create"));
		System.out.println("0. " + LanguageUtils.get("option.back.admin"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

}
