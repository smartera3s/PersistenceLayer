package persistenceObject;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exception.ConditionNotFoundException;
import exception.KeyNotFoundException;

public class PersistenceObject {
	
	private ArrayList<JSONObject> persistenceObjects	=	new ArrayList<JSONObject>();
	
	public int size(){
		return this.persistenceObjects.size();
	}
	
	public ArrayList<JSONObject> getAll(){
		return this.persistenceObjects;
	}
	
	public JSONObject get(int i){
		return this.persistenceObjects.get(i);
	}
	
	public PersistenceObject append(JSONObject objectToPersiste) {
		
		this.persistenceObjects.add(objectToPersiste);
		return this;
	}
	
	
	
	public PersistenceObject addAllFrom(JSONArray dataToPersiste,
			ArrayList<String> keyNamesToBeFlatten,
			ArrayList<String> excludedKeyNames) throws ConditionNotFoundException, KeyNotFoundException {
		/*
		 * loop through all the JSON array, check if it has any of the excluded key names
		 * 		
		*/
		
		JSONArray dataToPersistExcludingKeyNames	=	new JSONArray();
		dataToPersistExcludingKeyNames				=	this.exclude(dataToPersiste, excludedKeyNames);
		
		this.addAllFrom(dataToPersistExcludingKeyNames, keyNamesToBeFlatten);
		
		return this;
	}	
	
	//flat certain keyNames then append the json object
	public PersistenceObject addAllFrom(JSONArray dataToPersiste,
			ArrayList<String> keyNamesToBeFlattenIfExist) throws ConditionNotFoundException, KeyNotFoundException {
		
		for (int i = 0; i < dataToPersiste.length(); i++) {
			
			JSONObject currentJSONObjectInLoop;
			try {
				currentJSONObjectInLoop = dataToPersiste.getJSONObject(i);
				if(keyNamesToBeFlattenIfExist.contains(currentJSONObjectInLoop.get("name"))){
					JSONObject condition	=	this.flattenKeyInJsonObject(currentJSONObjectInLoop, currentJSONObjectInLoop.getString("name"));	
					this.append(condition);
				}else{
					
					this.append(currentJSONObjectInLoop);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return this;
	}
	
	public PersistenceObject addAllFrom(JSONArray dataToPersiste) {
			
			for (int i=0; i<dataToPersiste.length();i++){	
				//TODO-r handle these with custom exceptions
				try {
					this.append(dataToPersiste.getJSONObject(i));
				} catch (JSONException e) {
					
					//use error handler to print custom error message in out loger 
					//throw then a custom exception of our definition
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return this;
		}


	//append certain KeyNames from the condition object then append it
	public PersistenceObject appendFrom(
			JSONArray objectThatHoldsDataToBePersisted,
			ArrayList<String> KeyNamesToAppend) throws ConditionNotFoundException {
		
		for (int i = 0; i < KeyNamesToAppend.size(); i++) {
			
			String conditionName	=	KeyNamesToAppend.get(i);
			
			try {
				
				JSONObject condition	=	this.getSelectedNamedConditions(objectThatHoldsDataToBePersisted, conditionName);
				this.append(condition);
				
			} catch (ConditionNotFoundException e) {
				// TODO Auto-generated catch block
				throw e;
			}
		}
		
		return this;
	}

	//TODO-r don't forget to change the access modifier from public to private, it is set to public only for testing purposes
	public JSONArray exclude(JSONArray dataToPersist, ArrayList<String> excludedKeyNames) throws KeyNotFoundException{
		
		JSONArray dataToPersistExcludingKeyNames	=	new JSONArray();
		
		for (int i = 0; i < dataToPersist.length(); i++) {
		
			JSONObject currentConditionInLoop	=	new JSONObject();
		
			try {
				currentConditionInLoop				=	dataToPersist.getJSONObject(i);
				
				if(!excludedKeyNames.contains(currentConditionInLoop.get("name"))) dataToPersistExcludingKeyNames.put(currentConditionInLoop);
				
			} catch (JSONException e) {
				throw new KeyNotFoundException("Key 'name' Not Found: JSONObject at Index " + i );
			}
		}
		
		return dataToPersistExcludingKeyNames;
	}

	//TODO-r don't forget to change the access modifier from public to private, it is set to public only for testing purposes
	public JSONObject flattenKeyInJsonObject(JSONObject jsonObject, String keyToBeFlatten) throws KeyNotFoundException{
		
		//Handle with a Custom Exception
		try {
			if(jsonObject.get(keyToBeFlatten) instanceof JSONObject){
				
				JSONObject flattenedObject	=	new JSONObject();
				flattenedObject				=	(JSONObject) jsonObject.get(keyToBeFlatten);
				
				jsonObject.remove(keyToBeFlatten);
				jsonObject	=	extractJsonObjectKeyValuePairsAndAppendItToAnotherJsonObject(jsonObject, flattenedObject);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			throw new KeyNotFoundException("can't find key: " + keyToBeFlatten);
		}
		
		return jsonObject;
	}
	
	private JSONObject getSelectedNamedConditions(JSONArray conditions, String conditionName) throws ConditionNotFoundException {		
			
			for (int i = 0; i < conditions.length(); i++) {
				
				JSONObject condition	=	new JSONObject();
				
				try {
					condition	=	conditions.getJSONObject(i);
					if(condition.get("name").equals(conditionName)){
						return condition;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			throw new ConditionNotFoundException("Condition Name is not found");		
		}	

	private JSONObject extractJsonObjectKeyValuePairsAndAppendItToAnotherJsonObject(JSONObject appendedToJsonObject, JSONObject appendedFromJsonObject){
		for (String key : JSONObject.getNames(appendedFromJsonObject)){
			
			//TODO-r handle with custom exceptions unformattedConditionObject
			try {
				appendedToJsonObject.put(key, appendedFromJsonObject.get(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return appendedToJsonObject;
	}
	
	
}
