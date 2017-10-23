package Query;

import configuration.PersistenceConfiguration;
import storageLayerUtils.DatabaseProperties;

public class QueryFactory {
	
	public static Query QueryBuilder(){
			
			DatabaseProperties dbProperties = DatabaseProperties.getInstance();
			if(dbProperties.getDbDriver().equals("mongo")){
				
				return new MongoQuery();
	
			}else{
				
				return new MongoQuery();
			}
		}
}
