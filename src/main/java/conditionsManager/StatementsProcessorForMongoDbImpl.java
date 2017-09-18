package conditionsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exception.ConditionException;
import exception.MissingConditionParameterException;

public class StatementsProcessorForMongoDbImpl extends Statement {
	
	private final static HashMap<String, String> mongoOperators;
	static{
		mongoOperators= new HashMap<String, String>();
		mongoOperators.put("<", "$lt");
		mongoOperators.put(">", "$gt");
		mongoOperators.put("<=", "$lte");
		mongoOperators.put(">=","$gte");
		mongoOperators.put("=","=");
	}
	


	@Override
	public Statement appendFromJsonObject(JSONObject JSONCondition) throws MissingConditionParameterException, ConditionException{
		//TODO-r handle throwing exception unFormatted condition
		//TODO-r handling if the Condition is formatted or not, should be placed inside condition class itself no in Statement class
			this.isFormattedCondition(JSONCondition);
		
		PersistenceCondition tempCondition	=	PersistenceConditionFactory.getConditionsManagerFactoryInstance();
		tempCondition.setCondition(JSONCondition);
		tempCondition						=	processCondition(tempCondition);
		this.appendCondition(tempCondition);
		
		return this;
	}
	
	@Override
	protected  PersistenceCondition	processCondition(PersistenceCondition condition) throws ConditionException{
		//should Handle throwing a custom exception
		condition.put("operator", getMongoOperator(condition.getConditionOperator()));
		return condition;
	}
	


	private String getMongoOperator(final String operator){
		return StatementsProcessorForMongoDbImpl.mongoOperators.get(operator);
	}



	

}
