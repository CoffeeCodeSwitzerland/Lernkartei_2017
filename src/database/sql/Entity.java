package database.sql;

import java.sql.ResultSet;
import java.util.ArrayList;

import database.jdbc.SQLiteDriver;
import database.sql.Attribute.Datatype;
import debug.Logger;
import globals.Globals;

/**
 * @author WISS
 *
 */
public class Entity extends SQLiteDriver {

	final protected AttributeList myAttributes = new AttributeList();
	private String myTableName;
	private final static String KEY_NAME = "KEY_NAME";
	private final static String VALUE = "VALUE";
	
	private String lastSQLCommand;
	private ResultSet lastResultSet;
	private boolean isCreated = false;
	
	/**
	 * To create a DB table if it does not exist yet
	 * 
	 * @param tableName
	 * @param attributes
	 * @return 0, row count or -1 for error
	 */
	public int createTableIfNotExists() {
		if (setConnection()>=0) {
			//main.debug.Debugger.out("create table...");
			lastSQLCommand = SQLHandler.createTableIfNotExistsCommand(myTableName, myAttributes);
			isCreated = true;
			return executeCommand(lastSQLCommand); // -1, 0, 1, 2
		}
		return -2;
	}

	public String getMyTableName() {
		return myTableName;
	}

	public void setMyTableName(String myTableName) {
		this.myTableName = myTableName;
	}

//	private void myError(String error) {
//		Logger.out(myTableName + ": " + error);
//	}

	private void myError(String error, String param) {
		Logger.out(myTableName + ": " + error +", " + param);
	}

	private void myError(Exception e, String error) {
		Logger.out(e.getMessage(), myTableName + ": " + error);
	}

	private void myError(Exception e, String error, String param) {
		Logger.out(e.getMessage(), myTableName + ": " + error + ", " + param);
	}

	private void addBaseAttributes() {
		KeyAttribute k = new KeyAttribute(KEY_NAME, "", Datatype.TEXT);
		myAttributes.add(k);
		Attribute a = new Attribute(VALUE, "");
		myAttributes.add(a);
	}

	public void addAttributes(AttributeList aList) {
		if (isCreated) myError("is crated, no more attributes may be added!", aList.getCommaSeparatedList(true));
		else myAttributes.add(aList);
	}

	private void addPrimaryKey(String pkName) {
		PrimaryKey pk = getMyAttributes().getPKey();
		if (pk != null) pk.setValue(pkName);	
		else {
			pk = new PrimaryKey(pkName);
			myAttributes.add(pk);
		}
	}

	public Entity(String tabName) {
		isCreated = false;
		myTableName = tabName;
		addBaseAttributes();
		setDriver(Globals.db_name); // set other DB name, than default
		addPrimaryKey("PK_"+tabName);
	}

	public Entity(String tabName, String pkName) {
		this(tabName);
		if (pkName == null) addPrimaryKey("PK_"+tabName);
		else addPrimaryKey(pkName);
	}

	public Entity(String dbName, String tabName, String pkName) {
		this(tabName,pkName);
		setDriver(dbName); // set other DB name, than default
	}

	/**
	 * to delete a key with a specific value
	 * 
	 * @param key
	 * @return >0 for success, 0 for NOP or -1 for errors
	 */
	public int delKeyValue(String key, String value) {
		lastSQLCommand = "Try delete...";
		try {
			//createTableIfNotExists();
			debug.Debugger.out("DEBBBB");
			lastSQLCommand = SQLHandler.deleteEntryCommand(myTableName, key, value);
			return executeCommand(lastSQLCommand); // -1, 0, 1, 2
		} catch (Exception e) {
			myError(e,key,value);
		}
		return -1;
	}

	/**
	 * to delete a value
	 * 
	 * @param key
	 * @return >0 for success, 0 for NOP or -1 for errors
	 */
	public int delValue(String value) {
		return delKeyValue(VALUE, value);
	}

	/**
	 * to delete a key
	 * 
	 * @param key
	 * @return >0 for success, 0 for NOP or -1 for errors
	 */
	public int delKey(String key) {
		return delKeyValue(key, null);
	}

	/**
	 * @param tabName
	 * @param attName
	 * @param pkey
	 * @param value
	 * @return
	 */
	protected ResultSet seekInTable(String pKey, String value) {
		// return seekSQL("SELECT " + attName + " FROM " + tabName + " WHERE " +
		// pKey + " = '" + value + "'");
		setConnection();
		lastSQLCommand = SQLHandler.selectCommand(myTableName, pKey, value);
		return executeQuery(lastSQLCommand);
	}
	
	public String seekInTable(String returnKey, String seekKey, String seekValue) {
		// return seekSQL("SELECT " + returnKey + " FROM " + tabName + " WHERE " +
		// seekKey + " = '" + seekValue + "'");
		setConnection();
		lastSQLCommand = SQLHandler.selectCommand(myTableName, null, seekKey, seekValue);
		ResultSet rs = executeQuery(lastSQLCommand);
		try {
			if (rs.next()) {
				String s = rs.getString(returnKey);
				rs.close();
				return s;
			}
			myError("no values found!", lastSQLCommand);
		} catch(Exception e) {
			myError(e, lastSQLCommand);
		}
		return null;
	}

	/**
	 * Seek Value
	 * 
	 * @param value
	 * @return ResultSet or null on errors
	 */
	public ResultSet seekAttribute(AttributeList attributes) {
		try {
			createTableIfNotExists();
			lastSQLCommand = SQLHandler.selectCommand(myTableName, attributes);
			return executeQuery(lastSQLCommand);
		} catch (Exception e) {
			myError(e,attributes.getCommaSeparatedList(true));
		}
		return null;
	}

	/**
	 * Set a value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public int setValue(String value) {
		try {
			setConnection();
			AttributeList attributes = new AttributeList();
			Attribute a = new Attribute(VALUE, value);
			attributes.add(a);
			lastResultSet = seekAttribute(attributes);
			if (lastResultSet != null && !lastResultSet.next()) {
				lastResultSet.close();
				// Einfügen des Datensatze, wenn keins da
				lastSQLCommand = SQLHandler.insertIntoTableCommand(myTableName, attributes);
				return executeCommand(lastSQLCommand);
			} else {
				lastResultSet.close();
			}
		} catch (Exception e) {
			myError(e,value);
		}
		return -1;
	}
	
	public int setValue(int value) {
		return setValue(Integer.toString(value));
	}
	
	public String getValue (String query) {
		return seekInTable(VALUE, KEY_NAME, query);
	}
	/**
	 * set or update an new key with that value
	 * 
	 * @param key
	 * @param value
	 * @return >0 for success, 0 for NOP or -1 for errors
	 */
	private int replaceOrInsertToken(	AttributeList aList, String kName, String key, 
										String vName, String value) {
		KeyAttribute k = new KeyAttribute(kName, key, Datatype.TEXT);
		Attribute a = new Attribute(vName, value);
		Attribute s = aList.seekKeyNamed(kName);
		if (s != null) s.setValue(key);
		else aList.add(k);
		Attribute sa = aList.seekKeyNamed(vName);
		if (sa != null) sa.setValue(value);
		else aList.add(a);
		// Überprüfen ob bereits ein Token vorhanden ist, wenn ja, überschreiben
		lastSQLCommand = SQLHandler.selectCommand(myTableName, aList, k);
		lastResultSet = executeQuery(lastSQLCommand);
		try {
			setConnection();
			if (lastResultSet != null && lastResultSet.next()) {
				// mindestens einen Eintrag gefunden:
				// TODO prüfen, dass es nur einer ist (sollte eigentlich, wenn
				// unique)...
				//
				// "UPDATE " + tabName + " SET " + vName + " = '" + value + "'"
				// + " WHERE " + kName + " = '" + key + "'";
				lastSQLCommand = SQLHandler.updateInTableCommand(myTableName,vName, value, kName, key);
			} else {
				// Kein Eintrag gefunden:
				// "INSERT INTO " + tabName + " (" + kName + ", " + vName + ") "
				// + "VALUES ('" + key
				// + "','" + value + "')";
				lastSQLCommand = SQLHandler.insertIntoTableCommand(myTableName,getMyAttributes(), kName, key, vName, value);
			}
			return executeCommand(lastSQLCommand);
		} catch (Exception e) {
			myError(e,"may not replaceOrInsert, problems with checking table!");
		}
		return -1;
	}

	/**
	 * Get the value of a key
	 * 
	 * @param key
	 *            --> the key name
	 * @return the value of that key, or null on errors or if key name not found
	 */
	public int setKeyValue(String key, String value) {
		try {
			createTableIfNotExists();
			return replaceOrInsertToken(getMyAttributes(), KEY_NAME, key, VALUE, value);
		} catch (Exception e) {
			myError(e, key, value);
		}
		return -1;
	}

	/**
	 * To insert a list of values into a table
	 * 
	 * @param tabName
	 * @param attributlist
	 * @param FK_ID
	 * @param moreValues
	 * @return 0, row count or -1 for error
	 */
//	protected int insertIntoTable(String attributList, String[] values) {
//		AttributeList attributes = new AttributeList();
//		String[] att = attributList.split(",");
//		for (int i = 0; i < values.length; i++) {
//			Attribute a = new Attribute(att[i], values[i]);
//			attributes.add(a);
//		}
//		setConnection();
//		lastSQLCommand = SQLHandler.insertIntoTableCommand(myTableName, attributes);
//		return executeCommand(lastSQLCommand);
//	}
//
	public ArrayList<String> getDataList(String query) {
		ArrayList<String> values = new ArrayList<String>();
		setConnection();
		lastResultSet = this.executeQuery(query);
		try {
			while (lastResultSet.next()) {
				values.add(lastResultSet.getString(0));
			}
			return values;
		} catch (Exception e) {
			myError(e, query);
		}
		return null;
	}

	public AttributeList getMyAttributes() {
		return myAttributes;
	}

	public String getLastSQLCommand() {
		return lastSQLCommand;
	}

	public ResultSet getLastResultSet() {
		return lastResultSet;
	}

	public void setLastSQLCommand(String lastSQLCommand) {
		this.lastSQLCommand = lastSQLCommand;
	}

	public void setLastResultSet(ResultSet lastResultSet) {
		this.lastResultSet = lastResultSet;
	}
	
}
