package entryPoint;

import controllers.LoginController;
import config.HibernateUtils;
import utils.LanguageUtils;

/**
 * Entry point of the application. 
 * NOTE: ¡PORFAVOR USAR INSTALLAPP ANTES DE ESTE MAIN! 
 * 
 */
public class Main {
	public static void main(String[] args) {
		try {
			HibernateUtils.getSession();
			System.out.flush();
			new LoginController().iniciar();
		} catch (Exception e) {
			System.out.println(LanguageUtils.get("error.main.startup") + " " + e.getMessage());
		} finally {
			HibernateUtils.shutdown();
		}
	}
}