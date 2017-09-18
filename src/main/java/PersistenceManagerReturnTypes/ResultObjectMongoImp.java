package PersistenceManagerReturnTypes;

import com.mongodb.DBObject;

public class ResultObjectMongoImp extends ResultObject{

	private DBObject	object;

	public DBObject getObject() {
		return object;
	}

	public void setObject(DBObject object) {
		this.object = object;
	}

	@Override
	public Object get(String key) {
		
		return object.get(key);
	}
}
