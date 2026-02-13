package entryPoint;

import controllers.LoginController;
import utils.HibernateUtils;

/**
 * This class is the entry point of the application
 */
public class Main {
	public static void main(String[] args) {
		try {
			HibernateUtils.getSession();// in this line the code in the static block runs.
			System.out.flush();
			new LoginController().iniciar();
			HibernateUtils.shutdown();// To make sure sessionFactory is closed
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}