package storageLayerUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import configuration.PersistenceConfiguration;


public class DatabaseProperties {
	
	private static DatabaseProperties instance;


	public static DatabaseProperties getInstance(){
		if(instance==null){
			instance=new DatabaseProperties(PersistenceConfiguration.get_DATABASE_PROPERTIES_PATH());

		}
			
		return instance;
	}
	
	private static String 	dbDriver;
	private static String 	dbName;
	private static String 	dbUserName;
	private static String 	dbPassword;
	private static String 	host;
	private static int		port;
	
	public String getDbDriver() {
		return dbDriver;
	}



	public static String getHost() {
		return host;
	}



	public static void setHost(String host) {
		DatabaseProperties.host = host;
	}



	public static int getPort() {
		return port;
	}



	public static void setPort(int port) {
		DatabaseProperties.port = port;
	}



	public static void setDbDriver(String dbDriver) {
		DatabaseProperties.dbDriver = dbDriver;
	}



	public String getDbName() {
		return dbName;
	}



	public static void setDbName(String dbName) {
		DatabaseProperties.dbName = dbName;
	}



	public String getDbUserName() {
		return dbUserName;
	}



	public static void setDbUserName(String dbUserName) {
		DatabaseProperties.dbUserName = dbUserName;
	}



	public String getDbPassword() {
		return dbPassword;
	}



	public static void setDbPassword(String dbPassword) {
		DatabaseProperties.dbPassword = dbPassword;
	}

	
	
	
	
	private   DatabaseProperties(String databasePropertiesPath){
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(databasePropertiesPath);
			prop.load(input);

			//should I define my custome exceptions and handle them manually or not ?
			setDbDriver(prop.getProperty("database_driver"));
			setDbName(prop.getProperty("database_name"));
			setDbUserName(prop.getProperty("database_username"));
			setDbPassword(prop.getProperty("database_password"));
			setHost(prop.getProperty("host"));
			setPort(Integer.parseInt(prop.getProperty("port")));

		} catch (IOException ex) {
			ex.printStackTrace();
			
		} finally {
			if (input != null) {
				try {
					input.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
}
