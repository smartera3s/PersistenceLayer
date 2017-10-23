package conditionsManager;

import storageLayerUtils.DatabaseProperties;


public class PersistenceConditionFactory {
	
	private static final DatabaseProperties dbProperties = DatabaseProperties.getInstance();
	private static String db_driver;

	public static PersistenceCondition getConditionsManagerFactoryInstance(){
		
		PersistenceConditionFactory.db_driver	=	PersistenceConditionFactory.dbProperties.getDbDriver();
		return new PersistenceCondition();
		
	}
	
	public static Statement getStatement(){
		
		PersistenceConditionFactory.db_driver	=	PersistenceConditionFactory.dbProperties.getDbDriver();
		
		if(dbProperties.getDbDriver().equals("mongo")){
			return new StatementsProcessorForMongoDbImpl();
			//return new PersistenceManagerMongoImp(MongoDB.getMongoInstance());

		}else{
			//TODO-r handle other drivers and handle a default value if Getter returns unImplemented database Driver Name
			return new	StatementsProcessorForMongoDbImpl();
		}
		
	}
	
	
	
	
//	private static class DatabaseProperties{
//		
//		public DatabaseProperties() {
//			// TODO Auto-generated constructor stub
//			this.loadproperties();
//		}
//		
//		private String dbDriver;
//		
//		public String getDbDriver() {
//			return dbDriver;
//		}
//
//		public void setDbDriver(String dbDriver) {
//			this.dbDriver = dbDriver;
//		}
//		
//		public void  loadproperties(){
//			Properties prop = new Properties();
//			InputStream input = null;
//	
//			try {
//	
//				input = new FileInputStream("database.properties");
//				prop.load(input);
//	
//				//should I define my custome exceptions and handle them manually or not ?
//				setDbDriver(prop.getProperty("database_driver"));
//	
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			} finally {
//				if (input != null) {
//					try {
//						input.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//	
//		}
//	}
}
