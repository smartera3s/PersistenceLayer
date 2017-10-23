package storageLayerUtils;

import java.net.UnknownHostException;
import java.util.UUID;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.smartera.socialhub.ErrorHandler;

import configuration.PersistenceConfiguration;

public class MongoDB {
	private static  DB db;
	private static  MongoClient mongoClient;
	private static UUID uuid;
	private static MongoDB instance;
	
		
	private MongoDB (){
		try {
			DatabaseProperties dbProperties = DatabaseProperties.getInstance();
			mongoClient = new MongoClient(dbProperties.getHost(), dbProperties.getPort());
			db = mongoClient.getDB(dbProperties.getDbName());

		} catch (UnknownHostException e) {
			ErrorHandler.handleError("failed to establish Mongo connection on port 27017", e.getMessage(), e);
		}
	}
	
	
	public static MongoDB getInstance(){
		if(instance==null)
			instance=new MongoDB();
			
		return instance;
	}
	
	
	public static DB getDb() {
		return db;
	}


	public static void setDb(DB db) {
		MongoDB.db = db;
	}


	public static MongoClient getMongoClient() {
		return mongoClient;
	}


	public static void setMongoClient(MongoClient mongoClient) {
		MongoDB.mongoClient = mongoClient;
	}
}
