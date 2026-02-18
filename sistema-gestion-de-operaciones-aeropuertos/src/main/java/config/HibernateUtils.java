package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import utils.LanguageUtils;

/**
 * Helper class to obtain and manage the Hibernate SessionFactory.
 */
public class HibernateUtils {
	private static SessionFactory session;
	static {
		Properties dbProps = new Properties();
		try (InputStream input = HibernateUtils.class.getClassLoader().getResourceAsStream("config/db.properties")) {
			if (input == null) {
				throw new RuntimeException(LanguageUtils.get("msg.error.dbProperties"));
			}
			dbProps.load(input);
			StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("config/hibernate.cfg.xml")
					.applySetting("hibernate.connection.url", dbProps.getProperty("db.url"))
					.applySetting("hibernate.connection.username", dbProps.getProperty("db.username"))
					.applySetting("hibernate.connection.password", dbProps.getProperty("db.password")).build();
			session = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(LanguageUtils.get("msg.error.ErronInitiatingSession") + e);
		}
	}

	public static SessionFactory getSession() {
		return session;
	}

	public static void shutdown() {
		if (session != null) {
			session.close();
		}
	}
}
