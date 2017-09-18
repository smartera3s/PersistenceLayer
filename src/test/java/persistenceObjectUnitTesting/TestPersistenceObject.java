package persistenceObjectUnitTesting;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import persistenceObject.PersistenceObject;
import conditionsManager.PersistenceConditionFactory;
import exception.ConditionNotFoundException;
import exception.KeyNotFoundException;
import exception.MissingConditionParameterException;

public class TestPersistenceObject {
	
	private PersistenceObject	persistenceObject;
	private JSONObject			simpleJsonObjectToAppend;
	private JSONArray			jsonArrayToTestAddAllFrom;
	private ArrayList<String>	keyNamesToBeExcluded;
	private ArrayList<String>	KeynamesToBeFlatten;
	private ArrayList<String>	KeynamesToBeFlattenThatDoesNotExist;
	private ArrayList<String>	keyNamesToAppend;
	private ArrayList<String>	keyNamesToAppendThatDoesnotExist;


	
	//what about {"option" : "OR", "conditions": [
		//{'name':'addComment2', 'operand1':'comment_creation_time_unix', 'operator': '>', 'oprand2': '9536624'},
		
		//{"option" : "and/or/none", "conditions": [
		//{'name':'addComment', 'operand1':'comment_creation_time_unix', 'operator': '>', 'oprand2': '9536624'},
		//{'name':'addComment2', 'operand1':'comment_creation_time_unix', 'operator': '>', 'oprand2': '9536624'}]} 
	//]}

	//functions to be tested
	//1- append(JSONObject objectToPersiste) returns PersistenceObject DONE
	//2- PersistenceObject addAllFrom(JSONArray dataToPersiste)	DONE
	//3- flattenKeyInJsonObject(JSONObject jsonObject, String keyToBeFlatten)	DONE
	//4- exclude(JSONArray dataToPersist, ArrayList<String> excludedKeyNames)	DONE
	//5- PersistenceObject addAllFrom(JSONArray dataToPersiste, ArrayList<String> keyNamesToBeFlatten)	DONE
	//6- PersistenceObject addAllFrom(JSONArray dataToPersiste, ArrayList<String> keyNamesToBeFlatten, ArrayList<String> excludedKeyNames) DONE
	//7- PersistenceObject appendFrom( JSONArray objectThatHoldsDataToBePersisted, ArrayList<String> KeyNamesToAppend)	DONE
	
	@Before
	public void setup() throws Exception{
		
		keyNamesToBeExcluded	=	new ArrayList<String>(){{
			
			add("getCommentsByDate1");
		}};
		
		keyNamesToAppend		=	new ArrayList<String>(){{
			
			add("getCommentsByDate2");
			add("nested");

		}};
		
		keyNamesToAppendThatDoesnotExist	=	new ArrayList<String>(){{
			
			add("KeyNameThatDoesnotExist");

		}};
		
		KeynamesToBeFlatten		=	new ArrayList<String>(){{
			
			add("nested");
		}};
		
		KeynamesToBeFlattenThatDoesNotExist	=	new ArrayList<String>(){{
			
			add("keyNameThatDoesNotExist");
		}};
		
		persistenceObject			=	new PersistenceObject();
		simpleJsonObjectToAppend	=	new JSONObject();
		simpleJsonObjectToAppend.put("name", "getCommentsByDate");
		
		jsonArrayToTestAddAllFrom	=	new JSONArray();
		
		for(int i = 0; i <= 3; i++) {

			 JSONObject tempCondition = new JSONObject();
			 tempCondition.put("name", "getCommentsByDate"+i);
			 jsonArrayToTestAddAllFrom.put(tempCondition);
        }
		
		JSONObject temp	=	new JSONObject();
		temp.put("key", "value");
		temp.put("key1", "value");
		
		JSONObject nestedObject	=	new JSONObject();
		nestedObject.put("nested",temp);
		nestedObject.put("name","nested");

		jsonArrayToTestAddAllFrom.put(nestedObject);
	}

	@Test
	public void append_whenGivenJsonObjectItWillBeAppendedToPersistenceObjectInstance(){
		
		persistenceObject.append(simpleJsonObjectToAppend);
		assertEquals(1, persistenceObject.size());
	}
	
	@Test
	public void append_returnsPersistenceObjectInstance(){
		
		assertThat(persistenceObject.append(simpleJsonObjectToAppend), instanceOf(PersistenceObject.class));
	}
	
	@Test
	public void addAllFrom_JSONArray_willPersisteJsonArrayToPersistenceObjectProberly(){
		
		persistenceObject.addAllFrom(jsonArrayToTestAddAllFrom);
		assertEquals(jsonArrayToTestAddAllFrom.length(), persistenceObject.size());
		
	}
	
	@Test
	public void flattenKeyInJsonObject_willFlatJsonObject() throws JSONException, KeyNotFoundException{
		
		JSONObject temp	=	new JSONObject();
		temp.put("key", "value");
		
		JSONObject nestedObject	=	new JSONObject();
		nestedObject.put("nested",temp);
		
		assertEquals(temp.toString(), persistenceObject.flattenKeyInJsonObject(nestedObject,"nested").toString());
		
	}
	
	@Test(expected = KeyNotFoundException.class)
	public void flattenKeyInJsonObject_willFailIfKeyDoesnotExist() throws Exception{
		
		JSONObject temp	=	new JSONObject();
		temp.put("key", "value");
		
		JSONObject nestedObject	=	new JSONObject();
		nestedObject.put("nested",jsonArrayToTestAddAllFrom);
		
		 persistenceObject.flattenKeyInJsonObject(nestedObject,"neted");
		
	}
	
	//TODO-r should I handle flatten a JSON Array
	
	@Test(expected = KeyNotFoundException.class)
	public void exclude_willThowKeyNotFoundExceptionIfJsonObjectDoesNotHaveNameAsKey() throws Exception{
		
		jsonArrayToTestAddAllFrom.getJSONObject(4).remove("name");
		persistenceObject.exclude(jsonArrayToTestAddAllFrom, keyNamesToBeExcluded);
	}
	
	@Test
	public void exclude_willReturnNewJSONArrayWithoutObjectsThatHasCertainNameIfFound() throws KeyNotFoundException, JSONException{
		
		JSONArray	expectedJsonArray	=	new JSONArray();
		
		for(int i = 0; i <= 3; i++) {

			 JSONObject tempCondition = new JSONObject();
			 if(i != 1)
			 {
			 	tempCondition.put("name", "getCommentsByDate"+i);
			 	expectedJsonArray.put(tempCondition);

			 }
        }
		
		JSONObject temp	=	new JSONObject();
		temp.put("key", "value");
		temp.put("key1", "value");
		
		JSONObject nestedObject	=	new JSONObject();
		nestedObject.put("nested",temp);
		nestedObject.put("name","nested");

		expectedJsonArray.put(nestedObject);
		
		JSONArray	returnedObjectFromExclude	=	new	JSONArray();
		returnedObjectFromExclude				=	persistenceObject.exclude(jsonArrayToTestAddAllFrom, keyNamesToBeExcluded);

		assertEquals(expectedJsonArray.toString(), returnedObjectFromExclude.toString());
	}
	
	
	//TODO-r should I thow an exception if the key to be flatten doesn't exist ?
	
	@Test
	public void addAllFrom_FlatJSONObjectsWithKeyNamesWillSuccessIfKeyNamesExists() throws ConditionNotFoundException, KeyNotFoundException, JSONException{
		
		persistenceObject.addAllFrom(jsonArrayToTestAddAllFrom, KeynamesToBeFlatten);
		
		jsonArrayToTestAddAllFrom.getJSONObject(4).remove("nested");
		jsonArrayToTestAddAllFrom.getJSONObject(4).put("key", "value");
		jsonArrayToTestAddAllFrom.getJSONObject(4).put("key1", "value");
		
		boolean areEqual	=	true;
		
		for(int i=0; i < persistenceObject.size(); i++){
			
			if(!persistenceObject.get(i).toString().equals(jsonArrayToTestAddAllFrom.getJSONObject(i).toString())){
				
				areEqual	=	false;
			}
		}
		assertTrue(areEqual);
	}
	
	@Test
	public void addAllFrom_willExcludeJsonObjectsWithProvidedExcludeKeyNamesThenFlatJsonObjectsWithProvidedKeyNamesToBeFlatten() throws ConditionNotFoundException, KeyNotFoundException, JSONException{
		JSONArray	expectedJsonArray	=	new JSONArray();
		
		for(int i = 0; i <= 3; i++) {

			 JSONObject tempCondition = new JSONObject();
			 if(i != 1)
			 {
			 	tempCondition.put("name", "getCommentsByDate"+i);
			 	expectedJsonArray.put(tempCondition);

			 }
        }
		
		JSONObject nestedObject	=	new JSONObject();
		nestedObject.put("name","nested");
		nestedObject.put("key", "value");
		nestedObject.put("key1", "value");

		expectedJsonArray.put(nestedObject);
				
		persistenceObject.addAllFrom(jsonArrayToTestAddAllFrom, KeynamesToBeFlatten, keyNamesToBeExcluded);
		
		boolean areEqual	=	true;
		
		for(int i=0; i < persistenceObject.size(); i++){
			
			if(!persistenceObject.get(i).toString().equals(expectedJsonArray.getJSONObject(i).toString())){
				
				areEqual	=	false;
			}
		}
		
		assertTrue(areEqual);
	}
	
	
	@Test(expected = KeyNotFoundException.class)
	public void addAllFrom_willThrowAnKeyNotFoundExceptionIfUnFormattedJsonObejct() throws ConditionNotFoundException, KeyNotFoundException, JSONException{
		
		jsonArrayToTestAddAllFrom.getJSONObject(4).remove("name");	
		persistenceObject.addAllFrom(jsonArrayToTestAddAllFrom, KeynamesToBeFlattenThatDoesNotExist, keyNamesToBeExcluded);
		
	}
	
	//TODO-r should I write another method to handle appedFrom with Flatten Option
	@Test
	public void appendFrom_willAppendSelectedJsonObjectsWithProvidedKeyNames() throws ConditionNotFoundException{
		
		persistenceObject.appendFrom(jsonArrayToTestAddAllFrom, keyNamesToAppend);
	}
	
	@Test(expected=ConditionNotFoundException.class)
	public void appendFrom_willThrowConditionNotFoundExceptionIfAnyOfKeyNamesIsNotFound() throws ConditionNotFoundException{
		
		persistenceObject.appendFrom(jsonArrayToTestAddAllFrom, keyNamesToAppendThatDoesnotExist);
	}
	
}
