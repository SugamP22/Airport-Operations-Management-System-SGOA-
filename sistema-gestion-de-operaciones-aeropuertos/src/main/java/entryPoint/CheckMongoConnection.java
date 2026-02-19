package entryPoint;

import config.MongoDbUtil;
import utils.LanguageUtils;

/**
 * Simple main to verify MongoDB connection (db.properties → MongoDbUtil).
 */
public class CheckMongoConnection {

	public static void main(String[] args) {
		try {
			MongoDbUtil.getDatabase().runCommand(org.bson.Document.parse("{ ping: 1 }"));
			System.out.println(LanguageUtils.get("info.mongo.connection.ok"));
		} catch (Exception e) {
			System.err.println(LanguageUtils.get("error.mongo.connection.failed") + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			MongoDbUtil.shutdown();
		}
	}
}
