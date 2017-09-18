package exception;

import exception.ExceptionUtils;

public class ConditionException extends Exception {

	public ConditionException(String message){
	     super(message);
	  }
	
	public ConditionException(Throwable e){
		super (ExceptionUtils.getStackTrace(e));
	}
}
