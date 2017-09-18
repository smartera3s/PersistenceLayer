package mongojdbc;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import persistenceObject.PersistenceObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import conditionsManager.PersistenceCondition;
import conditionsManager.Statement;
import exception.QueryException;

public class MongoHelperFunctions {
	
		private final static 	HashMap<String, Integer> orderedBy;
		static{
			orderedBy	=	new HashMap<String, Integer>();
			orderedBy.put("desc",-1);
			orderedBy.put("asc", 1);
		}

		public static DBCollection setCollection(DB db ,String tableName){
			
			 return db.getCollection(tableName);		
		}
		
		public static BasicDBObject	setSortingCondition(PersistenceCondition sortCondition){
			
			BasicDBObject column	=	new BasicDBObject();
			column.put(sortCondition.getConditionColumnName(), orderBy(sortCondition.getConditionColumnValue()));
			return null;
		}
		
		public static int orderBy(String orderByValue){
			
			return MongoHelperFunctions.orderedBy.get(orderByValue); //TODO-r handle custom exception if orderByValue doesn't exist
		}
		
		public static BasicDBObject	setColumns(ArrayList<String> columnNames){
			
			BasicDBObject columns	=	new BasicDBObject();
			
			for (String column : columnNames) {
				columns.put(column,1);
			}
			
			return columns;
		}
		
		
		public static BasicDBObject	setWhereConditions(Statement conditionStatement) throws QueryException{
				
				BasicDBObject conditions	=	new BasicDBObject();
				
				for (int i = 0; i < conditionStatement.size(); i++) {
					PersistenceCondition	condition	=	conditionStatement.getCondition(i);
					if(condition.getConditionOperator().equals("=")){
						
						conditions.put(condition.getConditionColumnName(), condition.getConditionColumnValue());
					}
					else{
						throw new QueryException("[Query Exception] undefined mongo where clause operator " + condition.getConditionOperator());
					}
				}
				return conditions;
		}
		
		//TODO-urgent must be tested 
		public static DBObject PersistenceObjectToDBObject(PersistenceObject persistenceObject) throws JSONException{
			
			DBObject object	=	new BasicDBObject();
			
			for	(int i=0; i < persistenceObject.size(); i++){
				
				JSONObject currentJsonObject	=	persistenceObject.get(i);
				
				for (String key : JSONObject.getNames(currentJsonObject)){
					
					if(currentJsonObject.get(key) instanceof JSONObject){

						object.put(key, JSON.parse((currentJsonObject.get(key)).toString()));						
					
					}else if(currentJsonObject.get(key) instanceof JSONArray){
						
						object.put(key, JSON.parse((currentJsonObject.get(key)).toString()));						
					
					}else{
						object.put(key, currentJsonObject.get(key));						

					}
				}
			}
			
			return object;
		}
		
		
}
