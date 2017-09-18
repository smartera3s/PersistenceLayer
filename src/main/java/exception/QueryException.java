package exception;

public class QueryException extends Exception{
	
	public QueryException(String message){
	     super(message);
	  }
	
	public QueryException(Throwable e){
		super (ExceptionUtils.getStackTrace(e));
	}
}
