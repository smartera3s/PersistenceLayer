package queryUnitTesting;



import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import storageLayerUtils.MongoDB;
import PersistenceManagerReturnTypes.ResultList;
import Query.Query;

import com.mongodb.DB;
import com.mongodb.DBCollection;

import configuration.PersistenceConfiguration;
import exception.InvalidQueryOperationException;
import exception.QueryException;


public class InsertQueryUnitTesting {
	static{
		PersistenceConfiguration.initConnection("database.properties");

	}
	
	private MongoDB instance 	=	MongoDB.getInstance();
	private DB db				=	instance.getDb();
	
	
	private DBCollection collection;
	private JSONObject jsonObjectToBeInserted;

	@Before
	public void setup() throws JSONException{

		this.collection	=	db.getCollection("test");

		jsonObjectToBeInserted	=	new JSONObject();
		jsonObjectToBeInserted.put("key", "value");
		jsonObjectToBeInserted.put("key1", "test");
		jsonObjectToBeInserted.put("key3", "test");
	
	
	}
	
	@After
	public void clearResources(){
		
		this.collection.drop();

	}
	
	@Test
	public void insert_willInsertNewElementInDataBaseWhenGivenValidJSONObject() throws InvalidQueryOperationException, QueryException{

		Object output	=	Query.builder()
			 .table("test")
			 .insert(jsonObjectToBeInserted)
			 .execute();

		ResultList actual_output = Query.builder()
										.table("test")
										.find()
										.execute();

	  assertEquals(1,actual_output.toArray().size());

	}
	
	
	
}
