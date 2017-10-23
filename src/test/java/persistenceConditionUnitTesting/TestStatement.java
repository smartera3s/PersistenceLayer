package persistenceConditionUnitTesting;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import conditionsManager.PersistenceConditionFactory;
import conditionsManager.Statement;
import configuration.PersistenceConfiguration;
import exception.ConditionException;
import exception.ConditionNotFoundException;
import exception.MissingConditionParameterException;

public class TestStatement {
	
	private Statement statement;
	
	private JSONObject condition;
	private JSONObject unFormattedCondition;
	private JSONArray conditions;
	private ArrayList<String>	selectedNamedConditionsToBeAdded;
	private ArrayList<String>	selectedNamedConditionsToBeExcluded;
	private ArrayList<String>	selectedNamedConditionsToBeExcludedButDoesNotExistInTheConditionsArray;

	private ArrayList<String>	selectedNamedConditionsThatDoesnotExist;
	@Before
	public void setup() throws Exception{
		PersistenceConfiguration.initConnection("/home/bmostafa/jspyDatabase.properties");

		statement	=	PersistenceConditionFactory.getStatement();
		
		condition	=	new JSONObject();
		//{'name':'addComment', 'operand1':'comment_creation_time_unix', 'operator': '>', 'oprand2': '9536624'}
		condition.put("name", "getCommentsByDate");
		condition.put("operand1","comment_creation_time_unix");
		condition.put("operator","=");
		condition.put("operand2","9536624");
		
		unFormattedCondition	=	new JSONObject();
		//{'name':'addComment', 'operand1':'comment_creation_time_unix', 'operator': '>', 'oprand2': '9536624'}
		unFormattedCondition.append("name", "getCommentsByDate");
		unFormattedCondition.append("operand1","comment_creation_time_unix");
		unFormattedCondition.append("operator",">");
		
		conditions	=	new JSONArray();
		 
		for(int i = 0; i <= 3; i++) {

			 JSONObject tempCondition = new JSONObject();
			 tempCondition.put("name", "getCommentsByDate"+i);
			 tempCondition.put("operand1","comment_creation_time_unix");
			 tempCondition.put("operator",">");
			 tempCondition.put("operand2","9536624");
			 conditions.put(tempCondition);
         }
		
		 selectedNamedConditionsToBeAdded	=	new ArrayList<String>();
		 selectedNamedConditionsToBeAdded.add("getCommentsByDate0");
		 selectedNamedConditionsToBeAdded.add("getCommentsByDate1");
		 
		 selectedNamedConditionsThatDoesnotExist	=	new ArrayList<String>();
		 selectedNamedConditionsThatDoesnotExist.add("notFound1");
		 selectedNamedConditionsThatDoesnotExist.add("notFound2");
		 
		 selectedNamedConditionsToBeExcluded	=	new ArrayList<String>();
		 selectedNamedConditionsToBeExcluded.add("getCommentsByDate2");
		 selectedNamedConditionsToBeExcluded.add("getCommentsByDate3");
		 
		 selectedNamedConditionsToBeExcludedButDoesNotExistInTheConditionsArray	=	new ArrayList<String>();
		 selectedNamedConditionsToBeExcludedButDoesNotExistInTheConditionsArray.add("first item that doesn't exist in the conditions array ");
		 selectedNamedConditionsToBeExcludedButDoesNotExistInTheConditionsArray.add("2nd item that doesn't exist in the conditions array ");
		 selectedNamedConditionsToBeExcludedButDoesNotExistInTheConditionsArray.add("3rd item that doesn't exist in the conditions array ");

		
	}

	//appendFromJsonObject:	DONE
	//appendAllFromJsonArray(JSONArray condition)	DONE
	//ppendFromJsonArray(JSONArray conditions,ArrayList<String> selectedNamedConditionsToBeAdded)	DONE
	//appendFromJsonArrayExcept(JSONArray conditions,ArrayList<String> NamedConditionsToBeExcluded)	DONE
	//TODO-r appendFromJsonObject(PersistenceCondition)
	
	@Test
	public void appendFromJsonObject_formattedJsonObjectAddedProperly() throws Exception{

		statement.appendFromJsonObject(condition);
		assertEquals(1, statement.size());
	}
	
	@Test
	public void appendFromJsonObject_unFormattedJsonObjectWillNotBeAdded() throws ConditionException {
		
		try {
			statement.appendFromJsonObject(unFormattedCondition);
		} catch (MissingConditionParameterException e) {
			
			assertEquals(0, statement.size());
		}
	}
	
	@Test
	public void appendAllFromJsonArray_formattedJsonArrayAddedProperly() throws MissingConditionParameterException, ConditionException{
		
		statement.appendAllFromJsonArray(conditions);
		assertEquals(4, statement.size());
	}
	
	@Test
	public void appendAllFromJsonArray_unFormattedJsonArrayWillNotBeAddedProperly() throws  JSONException, ConditionException{
		conditions.getJSONObject(1).remove("name");
		try {
			statement.appendAllFromJsonArray(conditions);
		} catch (MissingConditionParameterException e) {
			// TODO Auto-generated catch block
			assertNotSame(conditions.length(), statement.size());
		}
	}
	//ppendFromJsonArray(JSONArray conditions,ArrayList<String> selectedNamedConditionsToBeAdded)

	@Test
	public void AppendFromJsonArray_conditionsWithKeyNamesWillBeAddedProperly() throws MissingConditionParameterException, ConditionNotFoundException, JSONException, ConditionException{
			
		statement.appendFromJsonArray(conditions, selectedNamedConditionsToBeAdded);	
			assertEquals(selectedNamedConditionsToBeAdded.size(), statement.size());
	}
	
	@Test
	public void AppendFromJsonArray_conditionsWithKeyNamesWillNotBeAddedProperly() throws MissingConditionParameterException, JSONException, ConditionException{
			
		try {
			statement.appendFromJsonArray(conditions, selectedNamedConditionsThatDoesnotExist);
		} catch (ConditionNotFoundException e) {
			// TODO Auto-generated catch block
			assertNotSame(selectedNamedConditionsThatDoesnotExist, statement.size());
		}	
	}
	
	@Test
	public void appendFromJsonArrayExceptIfExist_conditionsWithKeyNamesWillBeExcludedIfExist() throws MissingConditionParameterException, ConditionException{
			
			statement.appendFromJsonArrayExceptIfExist(conditions, selectedNamedConditionsToBeExcluded);
			assertEquals(conditions.length()-selectedNamedConditionsToBeExcluded.size(), statement.size());
	}
	
	@Test
	public void appendFromJsonArrayExceptIfExist_willappendAllArrayIfExcludedKeysIsNotFoundInConditionsList() throws MissingConditionParameterException, ConditionException{
			
			statement.appendFromJsonArrayExceptIfExist(conditions, selectedNamedConditionsToBeExcludedButDoesNotExistInTheConditionsArray);
			assertEquals(conditions.length(), statement.size());
	}
	
	
}
