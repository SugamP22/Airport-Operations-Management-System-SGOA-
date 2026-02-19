package entryPoint;

import config.MongoDbUtil;

/**
 * Simple main to verify MongoDB connection (db.properties → MongoDbUtil).
 * Run this class to check if the app connects to MongoDB Atlas.
 */
public class CheckMongoConnection {

	public static void main(String[] args) {
		try {
			MongoDbUtil.getDatabase().runCommand(org.bson.Document.parse("{ ping: 1 }"));
			System.out.println("MongoDB connection OK.");
		} catch (Exception e) {
			System.err.println("MongoDB connection FAILED: " + e.getMessage());
			e.printStackTrace();
		} finally {
			MongoDbUtil.shutdown();
		}
	}
}
