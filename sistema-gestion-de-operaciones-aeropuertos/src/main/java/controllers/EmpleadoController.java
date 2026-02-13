package controllers;

import utils.LanguageUtils;
import utils.MenuUtils;
import utils.ValidationUtils;

public class EmpleadoController {

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
		case 1 -> System.out.println("OPTION 1");
		case 2 -> System.out.println("OPTION 2");
		case 3 -> System.out.println("OPTION 3");
		case 0 -> System.out.println("OPTION 4");
		default -> System.out.println(LanguageUtils.get("error.numberFormat"));

		}
	}

}
