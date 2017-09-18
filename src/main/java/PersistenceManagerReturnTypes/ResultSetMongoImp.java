package PersistenceManagerReturnTypes;

import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.ReadPreference;

public class ResultSetMongoImp extends ResultList {

	private DBCursor dbCursor;
	private List<DBObject>	listDBObject;

	public List<DBObject> getListDBObject() {
		return listDBObject;
	}

	public void setListDBObject(List<DBObject> listDBObject) {
		this.listDBObject = listDBObject;
	}

	public DBCursor getDbCursor() {
		return dbCursor;
	}

	public ResultSetMongoImp setDbCursor(DBCursor dbCursor) {
		this.dbCursor = dbCursor;
		return this;
	}
	
	@Override
	public ResultSetMongoImp	toArray(){
		
		setListDBObject(getDbCursor().toArray());
		return this;
	}

	@Override
	public String toString() {
		
		return getListDBObject().toString();
	}

	@Override
	public ResultObject get(int index) {
		
		ResultObjectMongoImp	result	=	new ResultObjectMongoImp();
		result.setObject(listDBObject.get(index));
		return result;
	}

	@Override
	public int size() {
		
		return listDBObject.size();
	}
	
	

}
