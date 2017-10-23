package persistenceManager;


import configuration.PersistenceConfiguration;
import storageLayerUtils.DatabaseProperties;
import storageLayerUtils.MongoDB;

public class PersistenceManagerFactory {
	
	
	public static PersistenceManager getPersistenceManagerInstance(){
		
		DatabaseProperties dbProperties = DatabaseProperties.getInstance();
			
		if(dbProperties.getDbDriver().equals("mongo")){
			return new PersistenceManagerMongoImp(MongoDB.getInstance());
			//return new PersistenceManagerMongoImp(MongoDB.getMongoInstance());

		}else{
			//TODO-r handle other drivers and handle a default value if Getter returns unImplemented database Driver Name
			return new PersistenceManagerMongoImp(MongoDB.getInstance());
		}	
	}
	
	
}
