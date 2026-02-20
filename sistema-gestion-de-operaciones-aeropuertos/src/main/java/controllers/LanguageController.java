package controllers;

import utils.LanguageUtils;
import utils.MenuUtils;
import utils.ValidationUtils;

/**
 * Class that i use to select a language(i.e English or Spanish )
 */
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
