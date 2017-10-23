package configuration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PersistenceConfiguration {

	private	static	String DATABASE_PROPERTIES_PATH	=	null;
	private static	boolean	isDataBasePropertiesPathSet	=	false;

	public static String get_DATABASE_PROPERTIES_PATH() {
		return DATABASE_PROPERTIES_PATH;
	}

	private static void set_DATABASE_PROPERTIES_PATH(String dATABASE_PROPERTIES_PATH) {
		if(!isDataBasePropertiesPathSet){
			DATABASE_PROPERTIES_PATH = dATABASE_PROPERTIES_PATH;
		}
	}
	
	public static void initConnection(String databasePropertiesPath){
		PersistenceConfiguration.set_DATABASE_PROPERTIES_PATH(databasePropertiesPath);
	}
	
	
}
