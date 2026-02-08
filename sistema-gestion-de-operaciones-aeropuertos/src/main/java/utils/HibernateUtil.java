package utils;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			// 1. Load DB credentials from properties file
			Properties dbProps = new Properties();
			dbProps.load(HibernateUtil.class.getClassLoader().getResourceAsStream("db.properties"));

			// 2. Build the Service Registry
			// This combines XML settings with the dynamic properties
			StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml")
					.applySetting("hibernate.connection.url", dbProps.getProperty("db.url"))
					.applySetting("hibernate.connection.username", dbProps.getProperty("db.username"))
					.applySetting("hibernate.connection.password", dbProps.getProperty("db.password")).build();

			// 3. Build the SessionFactory from the registry
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

		} catch (Exception e) {
			// Log the error so we can see WHY it failed (DB down, wrong pass, etc.)
			e.printStackTrace();
			throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	// Recommended: Add a way to close the connection when the app stops
	public static void shutdown() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}