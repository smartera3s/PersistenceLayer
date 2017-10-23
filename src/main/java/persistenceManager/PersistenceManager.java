package persistenceManager;

import java.time.Instant;
import java.util.ArrayList;

import persistenceObject.PersistenceObject;
import PersistenceManagerReturnTypes.ResultList;
import conditionsManager.PersistenceCondition;
import conditionsManager.Statement;
import exception.ConditionException;
import exception.InvalidQueryOperationException;
import exception.MissingConditionParameterException;
import exception.QueryException;

public abstract class PersistenceManager {
	
	protected	String				_tableName					=	null;
	protected Statement				_whereConditionStatement	=	null;
	protected PersistenceCondition	_sortingConditionStatement	=	null;
	protected int					_limit						=	0;
	protected ArrayList<String>		_columnNames				=	new ArrayList<String>();
	protected String				_op							=	null;
	protected PersistenceObject		_persistenceObject			=	null;
	
	

	public PersistenceObject get_persistenceObject() {
		return _persistenceObject;
	}

	public void set_persistenceObject(PersistenceObject _persistenceObject) {
		this._persistenceObject = _persistenceObject;
	}

	public void add_columnNames(ArrayList<String> _columnNames) {
		this._columnNames.addAll(_columnNames);
	}
	
	public void add_columnName(String _columnName) {
		this._columnNames.add(_columnName);
	}
	
	public void set_op(String _op) {
		this._op = _op;
	}
	
	public void set_tableName(String _tableName) {
		this._tableName = _tableName;
	}
	
	
	public PersistenceManager	where(Statement conditionStatement){
		
		_whereConditionStatement	=	conditionStatement;
		return this;
	}
	
	public PersistenceManager	where(String columnName, String operator, String value) throws QueryException{
	
		try {
			
			_whereConditionStatement	=	buildStatement(columnName, operator, value);
		
		} catch (MissingConditionParameterException | ConditionException e) {
			
			throw new QueryException(e);
		}
		
		return this;
	}
	
	public PersistenceManager	where(String conditionName, String columnName, String operator, String value) throws QueryException{
		
		try {
			
			_whereConditionStatement	=	buildStatement(conditionName, columnName, operator, value);
		
		} catch (MissingConditionParameterException | ConditionException e) {
			
			throw new QueryException(e);
		}
		
		return this;
	}
	
	protected Statement	buildStatement(String columnName, String operator, String value) throws MissingConditionParameterException, ConditionException{
		
		Long 					unixTimeStamp	=	Instant.now().getEpochSecond();
		String					conditionName	=	columnName	+"_"+	unixTimeStamp;
					
		return buildStatement(conditionName, columnName, operator, value);
	}
	
	
	protected Statement	buildStatement(String conditionName, String columnName, String operator, String value) throws MissingConditionParameterException, ConditionException{
	
		//TODO-urgent  implement and multiple conditions input
		PersistenceCondition	condition		=	buildCondition(conditionName, columnName, operator, value);
		Statement 				statement		=	Statement.build().appendCondition(condition);
		
		return statement;
	}
	
	protected PersistenceCondition	buildCondition(String conditionName, String columnName, String operator, String value) throws MissingConditionParameterException, ConditionException{
		
		return PersistenceCondition.build().buildCondition(conditionName, columnName, operator, value);
	}

	
	public PersistenceManager	limit(int limit){
		
		_limit	=	limit;
		return this;
	}
	
	public PersistenceManager	sortDesc(String columnName) throws MissingConditionParameterException, ConditionException{
		
		Long 					unixTimeStamp	=	Instant.now().getEpochSecond();
		String					conditionName	=	columnName	+"_"+	unixTimeStamp;
		_sortingConditionStatement	=	buildCondition(conditionName, columnName, "=", "desc");
	
		return this;
	}
	
	public PersistenceManager	sortAsc(String columnName) throws MissingConditionParameterException, ConditionException{
		
		Long 					unixTimeStamp	=	Instant.now().getEpochSecond();
		String					conditionName	=	columnName	+"_"+	unixTimeStamp;
		_sortingConditionStatement	=	buildCondition(conditionName, columnName, "=", "asc");
	
		return this;
	}
	
	//TODO-r sorting conditions in much complex than this, this should be refactored
	public PersistenceManager	sort(PersistenceCondition sortingCondition){
		
		_sortingConditionStatement	=	sortingCondition;
		return this;
	}
	
	public abstract ResultList execute() throws InvalidQueryOperationException, QueryException;
	protected abstract void validateQuery();
}
