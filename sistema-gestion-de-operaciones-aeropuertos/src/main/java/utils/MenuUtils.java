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
		System.out.println(LanguageUtils.get("ui.login.option"));
		System.out.println(LanguageUtils.get("ui.exit.option"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");
	}

	/**
	 * options in langaugeMenu
	 */
	public static void languageMenu() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.lang.menu.title"), "=");
		System.out.println();
		System.out.println(LanguageUtils.get("ui.lang.option.en"));
		System.out.println(LanguageUtils.get("ui.lang.option.es"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

	/**
	 * options in menuEmpleado
	 */
	public static void menuEmpleado() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.title.employee"), "=");
		System.out.println();
		System.out.println(LanguageUtils.get("option.empleado.consultarVuelos"));
		System.out.println(LanguageUtils.get("option.empleado.reserva"));
		System.out.println(LanguageUtils.get("option.empleado.consultarClima"));
		System.out.println(LanguageUtils.get("option.salir"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

	/**
	 * options in menuAdmin
	 */
	public static void menuAdmin() {
		BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("ui.title.employee"), "=");
		System.out.println();
		System.out.println(LanguageUtils.get("option.admin.vuelos"));
		System.out.println(LanguageUtils.get("option.admin.pasajeros"));
		System.out.println(LanguageUtils.get("option.admin.meterologicos"));
		System.out.println(LanguageUtils.get("option.admin.empelados"));
		System.out.println(LanguageUtils.get("option.admin.descifrar"));
		System.out.println(LanguageUtils.get("option.admin.verificar"));
		System.out.println(LanguageUtils.get("option.salir"));
		System.out.println();
		BoxedMessageUtils.horizontalRow("*");

	}

}
