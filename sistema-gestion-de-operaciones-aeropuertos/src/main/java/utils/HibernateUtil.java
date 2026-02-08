package utils;

import java.io.FileInputStream;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			// Load DB credentials from properties file
			Properties dbProps = new Properties();
			dbProps.load(new FileInputStream("src/main/resources/db.properties"));

			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml"); // insert value into url,usuario and password
			configuration.getProperties().put("hibernate.connection.url", dbProps.getProperty("db.url"));
			configuration.getProperties().put("hibernate.connection.username", dbProps.getProperty("db.username"));
			configuration.getProperties().put("hibernate.connection.password", dbProps.getProperty("db.password"));
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
