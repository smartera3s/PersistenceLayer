package conditionsManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;


public class PersistenceConditionFactory {
	
	private static final DatabaseProperties dbProperties = new DatabaseProperties();
	private static String db_driver;

	public static PersistenceCondition getConditionsManagerFactoryInstance(){
		
		PersistenceConditionFactory.db_driver	=	PersistenceConditionFactory.dbProperties.getDbDriver();
		return new PersistenceCondition();
//		if(db_driver.equals("mongo")){
//			return new ConditionsProcessorForMongoDbImpl();
//		}if(db_driver.equals("mysql")){
//			return new ConditionsProcessorForMysqlDbImpl();
//		}else{
//			//TODO-r handle other drivers and handle a default value if Getter returns unImplemented database Driver Name
//			return new ConditionsProcessorForMongoDbImpl();
//		}
	}
	
	public static Statement getStatement(){
		
		PersistenceConditionFactory.db_driver	=	PersistenceConditionFactory.dbProperties.getDbDriver();
		
		if(db_driver.equals("mongo")){
			return new StatementsProcessorForMongoDbImpl();
		}else{
			//TODO-r handle other drivers and handle a default value if Getter returns unImplemented database Driver Name
			return new	StatementsProcessorForMongoDbImpl();
		}	
	}
	
	
	
	
	private static class DatabaseProperties{
		
		public DatabaseProperties() {
			// TODO Auto-generated constructor stub
			this.loadproperties();
		}
		
		private String dbDriver;
		
		public String getDbDriver() {
			return dbDriver;
		}

		public void setDbDriver(String dbDriver) {
			this.dbDriver = dbDriver;
		}
		
		public void  loadproperties(){
			Properties prop = new Properties();
			InputStream input = null;
	
			try {
	
				input = new FileInputStream("database.properties");
				prop.load(input);
	
				//should I define my custome exceptions and handle them manually or not ?
				setDbDriver(prop.getProperty("database_driver"));
	
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
}
