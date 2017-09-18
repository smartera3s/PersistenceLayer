package PersistenceManagerReturnTypes;



public abstract class ResultList {

	public abstract ResultList	toArray();
	
	public abstract String	toString();
	
	public abstract ResultObject	get(int index);
	
	public abstract int size();
}
