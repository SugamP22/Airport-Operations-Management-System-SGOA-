package controllers;

import authService.CurrentUser;
import entities.Departamento;
import entities.Empleado;
import repositories.EmpleadoDAO;
import utils.BoxedMessageUtils;
import utils.LanguageUtils;
import utils.Md5Util;
import utils.MenuUtils;
import utils.ValidationUtils;

/**
 * Main controller handling user authentication and session startup.
 */
public class LoginController {

	// Operational controllers for the airport system
	private static final WeatherController WEATHER_CONTROLLER = new WeatherController();
	private static final ReservationController RESERVATION_CONTROLLER = new ReservationController();
	private static final FlightsController FLIGHTS_CONTROLLER = new FlightsController();
	private static final LanguageController LANGAUECONTROLLER = new LanguageController();
	private static final PassengersController PASSENGERS_CONTROLLER = new PassengersController();

	// Role-based dashboard controllers
	private static final EmpleadoController EMPLEADO_CONTROLLER = new EmpleadoController(FLIGHTS_CONTROLLER,
			WEATHER_CONTROLLER, RESERVATION_CONTROLLER, PASSENGERS_CONTROLLER);
	private static final AdminController ADMIN_CONTROLLER = new AdminController(FLIGHTS_CONTROLLER, WEATHER_CONTROLLER,
			RESERVATION_CONTROLLER, PASSENGERS_CONTROLLER);

	private String username;
	private String password;

	/**
	 * Runs the main login loop and session management.
	 */
	public void iniciar() {
		while (true) {
			LANGAUECONTROLLER.updateLanguage();
			System.out.println();
			int loginOption = login();

			if (loginOption == 1) {
				System.out.println();
				MenuUtils.signInTitle();

				username = ValidationUtils.readString(LanguageUtils.get("ui.username"));
				password = ValidationUtils.readString(LanguageUtils.get("ui.password"));

				System.out.println();
				BoxedMessageUtils.horizontalRow("*");

				Empleado currentEmpleado = getEmpleado(username, password);
				if (currentEmpleado == null) {
					System.out.println(LanguageUtils.get("error.userNotFound"));
					continue;
				}

				CurrentUser.empleado = currentEmpleado;
				openDashboard(currentEmpleado);

			} else {
				System.out.println(LanguageUtils.get("system.closing"));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(LanguageUtils.get("system.closed"));
				break;
			}
		}

	}

	/**
	 * Redirects to the appropriate dashboard based on employee department.
	 */
	private void openDashboard(Empleado currentEmpleado) {
		if (currentEmpleado.getDepartamento() == Departamento.Gerencia) {
			ADMIN_CONTROLLER.openDashboard();
			return;
		}
		EMPLEADO_CONTROLLER.openDashboard();
	}

	/**
	 * Validates credentials by comparing MD5 hashed passwords.
	 */
	private Empleado getEmpleado(String username2, String password2) {
		Empleado emp = null;
		try {
			EmpleadoDAO dao = new EmpleadoDAO();
			String hash = Md5Util.hash(password2);
			emp = dao.findByUsuarioYClave(username2, hash);
		} catch (Exception e) {
			e.getMessage();
		}
		return emp;
	}

	/**
	 * Handles the initial login menu selection.
	 */
	private int login() {
		MenuUtils.loginMenu();
		while (true) {
			int numero = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			if (numero == 1 || numero == 0) {
				return numero;
			} else {
				System.out.println(LanguageUtils.get("error.invalidOption"));
			}

		}
	}

}