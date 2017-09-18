package persistenceManager;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

import persistenceObject.PersistenceObject;
import storageLayerUtils.MongoDB;
import PersistenceManagerReturnTypes.ResultList;
import PersistenceManagerReturnTypes.ResultSetMongoImp;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import mongojdbc.MongoHelperFunctions;
import conditionsManager.Statement;
import exception.InvalidQueryOperationException;
import exception.QueryException;

public class PersistenceManagerMongoImp extends PersistenceManager{
	
	
	private static 			MongoDB 	instance;
	private static 			DB 			db;
	
	public PersistenceManagerMongoImp(MongoDB mongoDbInstance) {
		
		this.instance	=	mongoDbInstance;
		this.db			=	instance.getDb();
	}

	private DBCursor _find(String tableName,
			ArrayList<String> columnNames, Statement conditionStatement) throws QueryException {
		
		DBCollection	collection		=	MongoHelperFunctions.setCollection(db, tableName);
		BasicDBObject 	columns			=	(columnNames == null ) 			? null : MongoHelperFunctions.setColumns(columnNames);
		BasicDBObject	conditions		=	(conditionStatement == null ) 	? null : MongoHelperFunctions.setWhereConditions(conditionStatement);
		DBCursor 		result 			=	collection.find( conditions, columns);
		
		return  result;
	}
	
	private void _insert(String tableName, PersistenceObject persistenceObject) throws JSONException{
		
		DBCollection	collection			=	MongoHelperFunctions.setCollection(db, tableName);
		DBObject		objectToBeInserted	=	MongoHelperFunctions.PersistenceObjectToDBObject(persistenceObject);
		
		collection.insert(objectToBeInserted);
		
	}
	
	public ResultList execute() throws InvalidQueryOperationException, QueryException {
		
		ResultSetMongoImp	resultSet	=	new	ResultSetMongoImp();
		DBCursor	dbCursor = null;
		
		if(_op 							!=	null){
			
			if(_op.equals("find")){
				
				if(_tableName					!=	null)	dbCursor	=	_find(_tableName, _columnNames,_whereConditionStatement);
				if(_sortingConditionStatement	!=	null)	dbCursor	=	dbCursor.sort(MongoHelperFunctions.setSortingCondition(_sortingConditionStatement));	
				if(_limit						>	0	)	dbCursor	=	dbCursor.limit(_limit);
				
				resultSet.setDbCursor(dbCursor).toArray();
			}
			else if (_op.equals("insert")){
							
				if(_tableName	!=	null){
					
					try {
						_insert(_tableName, _persistenceObject);
					
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						throw new QueryException(e);
					}
				}
			}
		}else{
			
			throw new InvalidQueryOperationException("you haven't specifid db operation");
		}
		
		return resultSet;
	}
	
	
	@Override
	protected void validateQuery() {
		
		
		
	}

}
