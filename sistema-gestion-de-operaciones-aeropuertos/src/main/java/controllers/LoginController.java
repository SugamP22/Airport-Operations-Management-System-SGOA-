package controllers;

import utils.BoxedMessageUtils;
import utils.LanguageUtils;
import utils.MenuUtils;
import utils.ValidationUtils;

public class LoginController {

	private static final LanguageController LANGAUECONTROLLER = new LanguageController();
	private static final EmpleadoController EMPLEADO_CONTROLLER = new EmpleadoController();
	private static final AdminController ADMIN_CONTROLLER = new AdminController();

	public void iniciar() {
		while (true) {
			LANGAUECONTROLLER.updateLanguage();
			System.out.println();
			int loginOption = login();
			if (loginOption == 1) {
				System.out.println();
				MenuUtils.signInTitle();
				String username = ValidationUtils.readString(LanguageUtils.get("ui.username"));
				String password = ValidationUtils.readString(LanguageUtils.get("ui.password"));
				System.out.println();
				BoxedMessageUtils.horizontalRow("*");
				int num = verifyuser(username, password);
				if (num == 1) {
					ADMIN_CONTROLLER.openDashboard(username);
				} else if (num == 2) {
					EMPLEADO_CONTROLLER.openDashboard(username);
				} else {
					System.out.println(LanguageUtils.get("error.userNotFound"));
					continue;
				}

			} else {
				System.out.println(LanguageUtils.get("system.closing"));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(LanguageUtils.get("system.closed"));
				break;
			}
		}

	}

	private int verifyuser(String username, String password) {
		try {
			if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
				return 1;
			} else if (username.equalsIgnoreCase("empleado") && password.equalsIgnoreCase("empleado")) {
				return 2;
			}

		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
		return 3;

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
