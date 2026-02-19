package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Utilidad para conectar con MongoDB
 */
public class MongoDbUtil {

	private static MongoClient client;
	private static String databaseName;
	private static String collectionClimaName;

	static {
		ensureClient();
	}

	/** (Re)connect if needed; used when entering weather module or after shutdown. */
	private static void ensureClient() {
		if (client != null) return;
		Properties props = new Properties();
		try (InputStream input = MongoDbUtil.class.getClassLoader().getResourceAsStream("config/db.properties")) {
			if (input == null) throw new RuntimeException("config/db.properties not found");
			props.load(input);
			String uri = props.getProperty("mongo.uri");
			if (uri == null || uri.isBlank()) throw new RuntimeException("mongo.uri is missing in config/db.properties");
			databaseName = props.getProperty("mongo.database", "aeropuertodb");
			collectionClimaName = props.getProperty("mongo.collection.clima", "datos_clima");
			client = MongoClients.create(uri);
		} catch (IOException e) {
			throw new ExceptionInInitializerError("Error loading MongoDB config: " + e.getMessage());
		}
	}

	public static MongoClient getClient() {
		ensureClient();
		return client;
	}

	public static MongoDatabase getDatabase() {
		ensureClient();
		return client.getDatabase(databaseName);
	}

	public static MongoCollection<Document> getClimaCollection() {
		return getDatabase().getCollection(collectionClimaName);
	}

	public static void shutdown() {
		if (client != null) {
			client.close();
			client = null;
		}
	}
}
