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

public class LoginController {

	private static final LanguageController LANGAUECONTROLLER = new LanguageController();
	private static final EmpleadoController EMPLEADO_CONTROLLER = new EmpleadoController();
	private static final AdminController ADMIN_CONTROLLER = new AdminController();
	private String username;
	private String password;
	private static CurrentUser user;

	public static CurrentUser getUser() {
		return user;
	}

	public void iniciar() {
		try {
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
					user = new CurrentUser(currentEmpleado);
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void openDashboard(Empleado currentEmpleado) {
		String departamento = currentEmpleado.getDepartamento().toString();
		if (departamento.equalsIgnoreCase(Departamento.Gerencia.toString())) {
			ADMIN_CONTROLLER.openDashboard();
			return;
		}
		EMPLEADO_CONTROLLER.openDashboard();
	}

	private Empleado getEmpleado(String username2, String password2) {
		EmpleadoDAO dao = new EmpleadoDAO();
		String hash = Md5Util.hash(password2);
		Empleado emp = dao.findByUsuarioYClave(username2, hash);
		return emp;
	}

	private int login() {
		MenuUtils.loginMenu();
		while (true) {
			int numero = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			if (numero == 1 || numero == 2) {
				return numero;
			} else {
				System.out.println(LanguageUtils.get("error.invalidOption"));
			}

		}
	}

}
