package utils;

import entities.Vuelo;

public class DayUtils {

	public static void selectDays(Vuelo vuelo) {
		char continueOption;
		do {
			char dayCode = Character.toUpperCase(ValidationUtils.readChar(LanguageUtils.get("flight.input.dias")));
			switch (dayCode) {
			case 'M':
			case 'L':
				vuelo.setLunes(true);
				break;
			case 'T':
				vuelo.setMartes(true);
				break;
			case 'W':
			case 'X':
				vuelo.setMiercoles(true);
				break;
			case 'R':
			case 'J':
				vuelo.setJueves(true);
				break;
			case 'F':
			case 'V':
				vuelo.setViernes(true);
				break;
			case 'S':
				vuelo.setSabado(true);
				break;
			case 'U':
			case 'D':
				vuelo.setDomingo(true);
				break;
			default:
				System.out.println(LanguageUtils.get("error.invalidOption"));
				break;
			}

			continueOption = Character.toUpperCase(ValidationUtils.readChar(LanguageUtils.get("flight.input.moreDays")));
		} while (continueOption == 'Y' || continueOption == 'S');

		if (!hasAnyDay(vuelo)) {
			System.out.println(LanguageUtils.get("error.empty"));
			selectDays(vuelo);
		}
	}

	private static boolean hasAnyDay(Vuelo vuelo) {
		return vuelo.isLunes() || vuelo.isMartes() || vuelo.isMiercoles() || vuelo.isJueves() || vuelo.isViernes()
				|| vuelo.isSabado() || vuelo.isDomingo();
	}
}
