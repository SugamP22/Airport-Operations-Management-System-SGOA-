package entryPoint;

import controllers.LoginController;
import config.HibernateUtils;

/**
 * Entry point of the application.
 */
public class Main {
	public static void main(String[] args) {
		try {
			HibernateUtils.getSession();
			System.out.flush();
			new LoginController().iniciar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			HibernateUtils.shutdown();
		}
	}
}