package mongoUnitTesting;

import static org.junit.Assert.*;
import mongojdbc.MongoHelperFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import persistenceObject.PersistenceObject;

public class mongoHelperFunctionsUnitTesting {

	private PersistenceObject persistence_object_to_be_transformed_into_DBObject;
	private PersistenceObject persistence_object_to_be_transformed_into_DBObject_with_nested_jsonObject;
	private PersistenceObject persistence_object_to_be_transformed_into_DBObject_with_nested_JSONArray;


	
	@Before
	public void setup() throws Exception{
		
		persistence_object_to_be_transformed_into_DBObject							=	new PersistenceObject();
		persistence_object_to_be_transformed_into_DBObject_with_nested_jsonObject	=	 new PersistenceObject();
		persistence_object_to_be_transformed_into_DBObject_with_nested_JSONArray	=	 new PersistenceObject();

		
		JSONObject  jsonObject_1	=	new JSONObject();
		jsonObject_1.put("name", "getCommentsByDate");
		jsonObject_1.put("operand1","comment_creation_time_unix");
		jsonObject_1.put("operator","=");
		jsonObject_1.put("operand2","9536624");
		
		JSONObject  jsonObject_2	=	new JSONObject();
		jsonObject_2.put("name_2", "getCommentsByDate");
		jsonObject_2.put("operand1_2","comment_creation_time_unix");
		jsonObject_2.put("operator_2","=");
		jsonObject_2.put("2ndObject", jsonObject_1);
		
		JSONArray jsonArray			=	new JSONArray();
		jsonArray.put(jsonObject_1);
		
		JSONObject	jsonObjectWithNestedJSONArray	=	new	JSONObject();
		jsonObjectWithNestedJSONArray.put("key one", "normal value");
		jsonObjectWithNestedJSONArray.put("key with JsonArray", jsonArray);
		
		
		persistence_object_to_be_transformed_into_DBObject.append(jsonObject_1);
		persistence_object_to_be_transformed_into_DBObject_with_nested_jsonObject.append(jsonObject_2);
		persistence_object_to_be_transformed_into_DBObject_with_nested_JSONArray.append(jsonObjectWithNestedJSONArray);

	}
	
	@Test 
	public void PersistenceObjectToDBObjectSuccessWhenGivenPersistenceObject() throws JSONException{
		
		DBObject	expected_output	=	new BasicDBObject("name", "getCommentsByDate");
		expected_output.put("operand1","comment_creation_time_unix");
		expected_output.put("operator","=");
		expected_output.put("operand2","9536624");
		
		DBObject 	actual_output	=	MongoHelperFunctions.PersistenceObjectToDBObject(persistence_object_to_be_transformed_into_DBObject);
		
		assertEquals(expected_output, actual_output);
	}
	
	@Test
	public void PersistenceObjectToDBObjectSuccessWhenGivenPersistenceObjectWithNestedJsonObjects() throws JSONException{
		
		JSONObject  jsonObject_1	=	new JSONObject();
		jsonObject_1.put("name", "getCommentsByDate");
		jsonObject_1.put("operand1","comment_creation_time_unix");
		jsonObject_1.put("operator","=");
		jsonObject_1.put("operand2","9536624");
		
		DBObject	expected_output	=	new BasicDBObject("name_2", "getCommentsByDate");
		expected_output.put("operand1_2","comment_creation_time_unix");
		expected_output.put("operator_2","=");
		expected_output.put("2ndObject", JSON.parse(jsonObject_1.toString()));
		
		DBObject 	actual_output	=	MongoHelperFunctions.PersistenceObjectToDBObject(persistence_object_to_be_transformed_into_DBObject_with_nested_jsonObject);

		assertEquals(expected_output, actual_output);
		
	}
	
	@Test
	public void PersistenceObjectToDBObjectSuccessWhenGivenPersistenceObjectWithNestedJSONArray() throws JSONException{
		
		JSONObject  jsonObject_1	=	new JSONObject();
		jsonObject_1.put("name", "getCommentsByDate");
		jsonObject_1.put("operand1","comment_creation_time_unix");
		jsonObject_1.put("operator","=");
		jsonObject_1.put("operand2","9536624");
		
		JSONArray jsonArray			=	new JSONArray();
		jsonArray.put(jsonObject_1);
		
		DBObject	expected_output	=	new BasicDBObject("key one", "normal value");
		expected_output.put("key with JsonArray", JSON.parse(jsonArray.toString()));
		
		DBObject 	actual_output	=	MongoHelperFunctions.PersistenceObjectToDBObject(persistence_object_to_be_transformed_into_DBObject_with_nested_JSONArray);
		
		assertEquals(expected_output, actual_output);
		
	}
}
