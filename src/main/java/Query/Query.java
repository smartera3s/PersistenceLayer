package Query;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistenceManager.PersistenceManager;
import persistenceManager.PersistenceManagerFactory;
import persistenceObject.PersistenceObject;

public abstract class Query {
	
	protected static PersistenceManager _persistenceManager;
		
	public static Query builder() {
			
		_persistenceManager	=	PersistenceManagerFactory.getPersistenceManagerInstance();
		
		return QueryFactory.QueryBuilder();
	}
	
	public Query table(String tableName){
	
		_persistenceManager.set_tableName(tableName);
		
		return this;
	}
	

	public PersistenceManager insert(JSONObject jsonObject) {
		
		_persistenceManager.set_persistenceObject(new PersistenceObject().append(jsonObject));
		_persistenceManager.set_op("insert");
		
		return _persistenceManager;
	}

	
	public PersistenceManager findOrInsertNew(String tableName) {
		
		//TODO the op should change to findOrInsertNew
		_persistenceManager.set_op("insert");
		
		return _persistenceManager;
	}
	
	
	public  PersistenceManager	find(String columnName){
		
		_persistenceManager.set_op("find");
		_persistenceManager.add_columnName(columnName);
		
		return _persistenceManager;
	}
		
	
	public PersistenceManager	find(ArrayList<String> columnNames){
	
		_persistenceManager.set_op("find");
		_persistenceManager.add_columnNames(columnNames);
		
		return _persistenceManager;
	}
	
	public PersistenceManager	find(){
		
		_persistenceManager.set_op("find");
		
		return _persistenceManager;
	}
}
