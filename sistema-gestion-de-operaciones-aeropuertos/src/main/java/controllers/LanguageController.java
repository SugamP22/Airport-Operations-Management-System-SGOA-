package controllers;

import utils.LanguageUtils;
import utils.MenuUtils;
import utils.ValidationUtils;

public class LanguageController {
	public void updateLanguage() {
		MenuUtils.languageMenu();
		while (true) {
			int numero = ValidationUtils.readInt(LanguageUtils.get("input.user"));
			if (numero == 1) {
				LanguageUtils.setLanguage("en");
				return;
			} else if (numero == 2) {
				LanguageUtils.setLanguage("es");
				return;
			} else {
				System.out.println(LanguageUtils.get("error.invalidOption"));
			}

		}

	}
}
