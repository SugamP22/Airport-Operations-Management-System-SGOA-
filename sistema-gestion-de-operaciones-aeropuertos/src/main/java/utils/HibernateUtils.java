package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * helper class to get SessionFactory
 */
public class HibernateUtils {
	private static SessionFactory session;
	static {
		Properties dbProps = new Properties();// open a container to load data from properties file
		try (InputStream input = HibernateUtils.class.getClassLoader().getResourceAsStream("db.properties")) {
			if (input == null) {
				throw new RuntimeException(LanguageUtils.get("msg.error.dbProperties"));
			}
			dbProps.load(input);// gets data from
								// properties file
			// configures xml file and insert value into it
			StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml")
					.applySetting("hibernate.connection.url", dbProps.getProperty("db.url"))
					.applySetting("hibernate.connection.username", dbProps.getProperty("db.username"))
					.applySetting("hibernate.connection.password", dbProps.getProperty("db.password")).build();
			session = new MetadataSources(registry).buildMetadata().buildSessionFactory();// inciates session
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(LanguageUtils.get("msg.error.ErronInitiatingSession") + e);

		}
	}

	/**
	 * using this method to call for session factory
	 * 
	 * @return
	 */
	public static SessionFactory getSession() {
		return session;
	}

	/**
	 * using this method to close the session if its still running in the background
	 */
	public static void shutdown() {
		if (session != null) {
			session.close();
		}
	}

}