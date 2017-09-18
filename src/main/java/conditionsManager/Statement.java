package conditionsManager;

import java.util.ArrayList;

import org.apache.log4j.spi.ThrowableInformation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exception.ConditionException;
import exception.ConditionNotFoundException;
import exception.MissingConditionParameterException;

public abstract class Statement  {
	
	private  ArrayList<PersistenceCondition> persistenceConditionArrayList	=	new ArrayList<PersistenceCondition>();
	
	public abstract Statement appendFromJsonObject(JSONObject condition) throws MissingConditionParameterException, ConditionException;

	protected abstract PersistenceCondition processCondition(PersistenceCondition condition) throws ConditionException;
	
	public static Statement build(){
		return PersistenceConditionFactory.getStatement();
		
	}
	
	public String	getColumnName(){
		return "";
	}
	
	protected Boolean isFormattedCondition(JSONObject condition) throws MissingConditionParameterException{
		
		if(!condition.has("name"))  		throw new MissingConditionParameterException("missing name parameter");
		else if(!condition.has("operand1")) throw new MissingConditionParameterException("missing operand1 parameter");
		else if(!condition.has("operator")) throw new MissingConditionParameterException("missing operator parameter");
		else if(!condition.has("operand2")) throw new MissingConditionParameterException("missing operand2 parameter");
		
		return true;
	}
	
	protected Boolean isFormattedCondition(PersistenceCondition condition) throws MissingConditionParameterException{
		
		if(!condition.has("name"))  		throw new MissingConditionParameterException("missing name parameter");
		else if(!condition.has("operand1")) throw new MissingConditionParameterException("missing operand1 parameter");
		else if(!condition.has("operator")) throw new MissingConditionParameterException("missing operator parameter");
		else if(!condition.has("operand2")) throw new MissingConditionParameterException("missing operand2 parameter");
		
		return true;
	}
	
	public Statement appendCondition(PersistenceCondition condition) throws MissingConditionParameterException, ConditionException{
		
		this.persistenceConditionArrayList.add(this.processCondition(condition));
		return this;
	}
	
	public PersistenceCondition getCondition(int index){
		return this.persistenceConditionArrayList.get(index);
	}
	
	public PersistenceCondition getCondition(String conditionName) throws ConditionNotFoundException, ConditionException{
		
		for (int i = 0; i < this.persistenceConditionArrayList.size(); i++) {
			
			PersistenceCondition currentCondition	=	new PersistenceCondition();
			currentCondition	=	this.persistenceConditionArrayList.get(i);
			if(currentCondition.has("name")){
		
				if(currentCondition.getString("name").equals(conditionName)){
					return currentCondition;
				}

			}
			else{
				//do something to tell the developer that this is unformatted condition object and it doesn't have a key = "name"
				throw new ConditionNotFoundException("can't find condition");
			}
		}
		
		//TODO-r handle approperiate return value if no condition exist with the passed name
		return null;
	}
	
	public void set(int index, PersistenceCondition condition){
		this.persistenceConditionArrayList.set(index, condition);
	}
	
	public int	size(){
		return this.persistenceConditionArrayList.size();
	}
	
	public PersistenceCondition get(int index){
		return this.persistenceConditionArrayList.get(index);
	}
	
	public Statement appendAllFromJsonArray(JSONArray conditions) throws MissingConditionParameterException, ConditionException {
		
		for (int i = 0; i < conditions.length(); i++) {
			JSONObject condition	=	new JSONObject();
				try {
					condition	=	conditions.getJSONObject(i);
					this.appendFromJsonObject(condition);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return this;	
	}
	
	
	public Statement appendFromJsonArray(JSONArray conditions,
			ArrayList<String> selectedNamedConditionsToBeAdded) throws MissingConditionParameterException, ConditionNotFoundException, JSONException, ConditionException  {
		
		//TODO-M 
		// Hashset requiredNames = ArrayToMap(selectedNamedConditionsToBeAdded);

		for (int i = 0; i < selectedNamedConditionsToBeAdded.size(); i++) {
			
			String conditionName	=	selectedNamedConditionsToBeAdded.get(i);
			
			try {
				JSONObject condition	=	this.getSelectedNamedConditions(conditions, conditionName);
				this.appendFromJsonObject(condition);
			} catch (ConditionNotFoundException e) {
				// TODO Auto-generated catch block
				throw e;
			}
		}
		

		 return this;
	}
	
	public Statement appendFromJsonArrayExceptIfExist(JSONArray conditions,
			ArrayList<String> NamedConditionsToBeExcluded) throws MissingConditionParameterException, ConditionException {

		for (int i = 0; i < conditions.length(); i++) {
			
			JSONObject condition	=	new JSONObject();
			try {
				condition	=	conditions.getJSONObject(i);
				
				if(NamedConditionsToBeExcluded.contains(condition.getString("name"))){
					continue;
				}else{
					this.appendFromJsonObject(condition);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return this;
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

	
	
}
