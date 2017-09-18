package storageLayerUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DatabaseProperties {
	
	private static DatabaseProperties instance;


	public static DatabaseProperties getInstance(){
		if(instance==null){
			instance=new DatabaseProperties();

		}
			
		return instance;
	}
	
	private static String dbDriver;
	
	public String getDbDriver() {
		return dbDriver;
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

	private static String dbName;
	private static String dbUserName;
	private static String dbPassword;
	
	
	
	private   DatabaseProperties(){
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("database.properties");
			prop.load(input);

			//should I define my custome exceptions and handle them manually or not ?
			setDbDriver(prop.getProperty("database_driver"));
			setDbName(prop.getProperty("database_name"));
			setDbUserName(prop.getProperty("database_username"));
			setDbPassword(prop.getProperty("database_password"));

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
