package conditionsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exception.ConditionException;
import exception.MissingConditionParameterException;

public  class PersistenceCondition {

	private JSONObject condition	=	new	JSONObject();
	
	public PersistenceCondition setCondition(JSONObject conditions){
		this.condition	=	conditions;
		return this;
	}
	
	public static PersistenceCondition build(){
		return PersistenceConditionFactory.getConditionsManagerFactoryInstance();
	}
	
	public PersistenceCondition buildCondition(String name, String operand1, String operator, String operand2) throws MissingConditionParameterException, ConditionException{
		//TODO-r handle building error array and send them all at once
		
		if(name ==	null || name.equals(""))  				throw new MissingConditionParameterException("missing name parameter");  				
		else if(operand1 == null || operand1.equals("")) 	throw new MissingConditionParameterException("missing column name parameter");
		else if(operator == null || operator.equals("")) 	throw new MissingConditionParameterException("missing operator parameter");
		else if(operand2 == null || operand2.equals("")) 	throw new MissingConditionParameterException("missing column value parameter");
				
		JSONObject	condition	=	new JSONObject();

		try {
			condition.put("name", name);
			condition.put("operand1", operand1);
			condition.put("operator", operator);
			condition.put("operand2", operand2);
			
		} catch (JSONException e) {
			
			throw new ConditionException(e);
		}
		
		
		this.setCondition(condition);
		return this;
	}
	
	
	public  String getConditionColumnName(){
		
		try {
			return this.condition.getString("operand1");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//handle custom exception
			e.printStackTrace();
			return null;
		}
	}
	
	public  String getConditionOperator(){
			
			try {
				return this.condition.getString("operator");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//handle custom exception
				e.printStackTrace();
				return null;
			}
		}
	
	public  String getConditionColumnValue(){
		
		try {
			return this.condition.getString("operand2");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//handle custom exception
			e.printStackTrace();
			return null;
		}
	}
	
	
	public String	getString(String keyName) throws ConditionException{
		
		try {
			return condition.getString(keyName);
		} catch (JSONException e) {
			
			throw new ConditionException(e);
		}
	}
	
	public Integer	getInteger(String	KeyName) throws ConditionException{
		
		try {
			return condition.getInt(KeyName);
		} catch (JSONException e) {
			throw new ConditionException(e);
		}
	}
	
	public Long	getLong(String KeyName) throws ConditionException{
		
		try {
			return condition.getLong(KeyName);
		} catch (JSONException e) {
			throw new ConditionException(e);
		}
	}
	
	public boolean	getBoolean(String KeyName) throws ConditionException{
			
			try {
				return condition.getBoolean(KeyName);
			} catch (JSONException e) {
				throw new ConditionException(e);
			}
		}
	
	public double	getDouble(String KeyName) throws ConditionException{
			
			try {
				return condition.getDouble(KeyName);
			} catch (JSONException e) {
				throw new ConditionException(e);
			}
		}
	
	public boolean has(String keyName){
		
		return condition.has(keyName);
	}
	
	public PersistenceCondition put(String key, String value) throws ConditionException{
		try {
			this.condition.put(key, value);
		} catch (JSONException e) {
			
			throw new ConditionException(e);
		}
		return this;
	}
	
	public PersistenceCondition put(String key, int value) throws ConditionException{
		try {
			this.condition.put(key, value);
		} catch (JSONException e) {
			throw new ConditionException(e);
		}
		return this;
	}
	
	public PersistenceCondition put(String key, boolean value) throws ConditionException{
		try {
			this.condition.put(key, value);
		} catch (JSONException e) {
			throw new ConditionException(e);
		}
		return this;
	}
	
	public PersistenceCondition put(String key, long value) throws ConditionException{
		try {
			this.condition.put(key, value);
		} catch (JSONException e) {
			throw new ConditionException(e);
		}
		return this;
	}
	
	public PersistenceCondition put(String key, double value) throws ConditionException{
		try {
			this.condition.put(key, value);
		} catch (JSONException e) {
			throw new ConditionException(e);
		}
		return this;
	}
	
	public int length(){
		return this.condition.length();
	}
	
	
	
}
