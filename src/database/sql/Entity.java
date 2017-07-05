package database.sql;

import java.util.ArrayList;

import database.jdbc.DBDriver;
import database.sql.AttributeInterface.Datatype;
import debug.Logger;

/**
 * Vorlage für eine Entität (instanzierbar): - besitzt automatisch und immer
 * eine Primary Key - besitzt automatisch einen Index (für zum Bsp. config.db) -
 * bietet Debug informationen, wie lastSQLCommand und lastResultSet an
 * 
 * @author hugo-lucca
 *
 */
public class Entity {

	final protected AttributeList myAttributes = new AttributeList();
	public final static String KEY_NAME = "KEY_NAME";
	public final static String VALUE_NAME = "KEY_VALUE";

	protected DBDriver myDBDriver; // should never be null
	private String myTableName;  // should never be null
	private boolean isCreated = false;

	/*
	 * TODO add a getSize() method... 
	 *      is needed in many cases, this is now performed in a more complicated way!
	 */
	
	/**
	 * To create a DB table if it does not exist yet
	 * 
	 * @param tableName
	 * @param attributes
	 * @return 0, row count or -1 for error
	 */
	public int createTableIfNotExists() {
		if (isCreated == true)
			return 0;
		int result = -2;
		result = myDBDriver.executeCommand(SQLHandler.createTableIfNotExistsCommand(myTableName, myAttributes));
		if (result >= 0) {
			; // -1, 0, 1, 2
			isCreated = true;
		}
		return result;
	}

	/**
	 * To add basic attributes
	 */
	private void addBaseAttributes() {
		KeyAttribute k = new KeyAttribute(KEY_NAME, "", Datatype.TEXT);
		myAttributes.addUnique(k);
		Attribute a = new Attribute(VALUE_NAME, "");
		myAttributes.addUnique(a);
	}

	/*
	 * To add a single attribute (only possible, if table is not created yet)
	 */
	public void addAttribute(Attribute a) {
		if (isCreated) {
			Logger.out("table is created yet, no more attributes may be added now", "attr: " + a.getName());
		} else {
			myAttributes.addUnique(a);
		}
	}

	/**
	 * To add a list of attributes (only possible, if table is not created yet)
	 * 
	 * @param aList
	 */
	public void addAttributes(AttributeList aList) {
		if (isCreated) {
			Logger.out("table is created yet, no more attributes may be added", aList.getCommaSeparatedList(true));
		} else {
			myAttributes.append(aList);
		}
	}

	/**
	 * To add the primary key
	 * 
	 * @param pkName
	 */
	private void addPrimaryKey(String pkName) {
		PrimaryKey pk = getMyAttributes().getPrimaryKey();
		if (pk != null)
			pk.setValue(pkName);
		else {
			pk = new PrimaryKey(pkName);
			myAttributes.addUnique(pk);
		}
	}

//	public void setDriver(String newDBName) {
//		if (newDBName != null) {
//			if (newDBName.endsWith(".db")) {
//				dbURL = newDBName;
//			} else {
//				dbURL = newDBName + ".db";
//			}
//		}
//	}
//
	/**
	 * Constructors
	 */
	public Entity(DBDriver newDBdriver, String tabName, boolean createIt) {
		myDBDriver = newDBdriver;
		isCreated = false;
		myTableName = tabName;
		addBaseAttributes();
		//setDriver(Globals.db_name); // set other DB name, than default
		addPrimaryKey("PK_" + tabName);
		// table will not be created here!
		if (createIt)
			createTableIfNotExists();
	}

	public Entity(DBDriver newDBdriver, String tabName, String pkName, boolean createIt) {
		this(newDBdriver, tabName, false);
		if (pkName == null)
			addPrimaryKey("PK_" + tabName);
		else
			addPrimaryKey(pkName);
		// table will not be created here!
		if (createIt)
			createTableIfNotExists();
	}

	public Entity(DBDriver newDBdriver, String dbName, String tabName, String pkName, boolean createIt) {
		this(newDBdriver, tabName, pkName, false);
		//setDriver(dbName); // set other DB name, than default
		// table will not be created here!
		if (createIt)
			createTableIfNotExists();
	}

	/**
	 * To get the PK_ID of this table using a unique key to find the row
	 * 
	 * @param door
	 * @return
	 */
	public int getEntityID(Attribute key) {
		if (key != null) {
			String pk_name = myAttributes.getPrimaryKey().getName();
			String keyName = key.getName();
			String cmd;
			if (key.isTEXT()) {
				cmd = SQLHandler.selectCommand(myTableName, pk_name, keyName, key.getValue());
			} else {
				cmd = SQLHandler.selectCommand(myTableName, pk_name, keyName, key.getIntValue());
			}
			debug.Debugger.out("Entity: '" + cmd);
			// Do "SELECT "+key.name+" FROM myTableName WHERE "+key.name+" =
			// " + key.value)
			myDBDriver.executeQuery(cmd);
			// assumed is only one key and only first returned (no check is
			// done)
			return myDBDriver.getResultPIntValueOf(pk_name);
		}
		Logger.out("invalid {null}-key for the SELECT PK_... command");
		return -1;
	}

	public int getEntityID(String key, String value) {
		Attribute a = new Attribute(key, value);
		return getEntityID(a);
	}

	public int getEntityID(String key, int value) {
		Attribute a = new Attribute(key, Integer.toString(value), Datatype.INT);
		return getEntityID(a);
	}

	/**
	 * to delete a key with a specific value
	 * 
	 * @param key
	 * @return >0 for success, 0 for NOP or -1 for errors
	 */
	public int delKeyValue(String key, String value) {
		return myDBDriver.executeCommand(SQLHandler.deleteEntryCommand(myTableName, key, value)); // -1, 0, 1, 2
	}

	/**
	 * to delete a value
	 * 
	 * @param key
	 * @return >0 for success, 0 for NOP or -1 for errors
	 */
	public int delValue(String value) {
		return delKeyValue(VALUE_NAME, value);
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
	protected boolean seekInTable(String pKey, String value) {
		// return seekSQL("SELECT " + attName + " FROM " + tabName + " WHERE " +
		// pKey + " = '" + value + "'");
		return myDBDriver.executeQuery(SQLHandler.selectCommand(myTableName, pKey, value));
	}

	public String seekInTable(String returnKey, String seekKey, String seekValue) {
		// return seekSQL("SELECT " + returnKey + " FROM " + tabName + " WHERE "
		// +
		// seekKey + " = '" + seekValue + "'");
		myDBDriver.executeQuery(SQLHandler.selectCommand(myTableName, null, seekKey, seekValue));
		try {
			if (myDBDriver.isThereAResult()) {
				return myDBDriver.getResultValueOf(returnKey);
			}
			Logger.out("no values found!", myDBDriver.getLastSQLCommand());
		} catch (Exception e) {
			Logger.out("Resultset is corrupt", myDBDriver.getLastSQLCommand());
		}
		return null;
	}

	/**
	 * Seek Value
	 * 
	 * @param value
	 * @return ResultSet or null on errors
	 */
	public boolean seekAttribute(AttributeList attributes) {
		return myDBDriver.executeQuery(SQLHandler.selectCommand(myTableName, attributes));
	}

	/**
	 * Set a value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public int setValue(String value) {
		AttributeList attributes = new AttributeList();
		Attribute a = new Attribute(VALUE_NAME, value);
		attributes.addUnique(a);
		seekAttribute(attributes);
		if (myDBDriver.isThereAResult()) {
			// Einfügen des Datensatze, wenn keins da
			return myDBDriver.executeCommand(SQLHandler.insertIntoTableCommand(myTableName, attributes));
		}
		return -1;
	}

	/**
	 * To set a single value using the index
	 * 
	 * @param value
	 * @return
	 */
	public int setValue(int value) {
		return setValue(Integer.toString(value));
	}

	/**
	 * To get a single value from the index
	 * 
	 * @param value
	 * @return
	 */
	public String getValue(String query) {
		return seekInTable(VALUE_NAME, KEY_NAME, query);
	}

	/**
	 * set or update an new key with that value
	 * 
	 * @param key
	 * @param value
	 * @return >0 for success, 0 for NOP or -1 for errors
	 */
	private int replaceOrInsertToken(AttributeList aList, String kName, String key, String vName, String value) {
		KeyAttribute k = new KeyAttribute(kName, key, Datatype.TEXT);
		Attribute a = new Attribute(vName, value);
		Attribute s = aList.getAttributeNamedAs(kName);
		if (s != null)
			s.setValue(key);
		else
			aList.addUnique(k);
		Attribute sa = aList.getAttributeNamedAs(vName);
		if (sa != null)
			sa.setValue(value);
		else
			aList.addUnique(a);
		// Überprüfen ob bereits ein Token vorhanden ist, wenn ja, überschreiben
		myDBDriver.executeQuery(SQLHandler.selectCommand(myTableName, aList, k, null));
		String cmd;
		if (myDBDriver.isThereAResult()) {
			// mindestens einen Eintrag gefunden:
			// TODO prüfen, dass es nur einer ist (sollte eigentlich, wenn unique)...
			//
			// "UPDATE " + tabName + " SET " + vName + " = '" + value + "'"
			// + " WHERE " + kName + " = '" + key + "'";
			cmd = SQLHandler.updateInTableCommand(myTableName, vName, value, kName, key);
		} else {
			// Kein Eintrag gefunden:
			// "INSERT INTO " + tabName + " (" + kName + ", " + vName + ") "
			// + "VALUES ('" + key
			// + "','" + value + "')";
			cmd = SQLHandler.insertIntoTableCommand(myTableName, getMyAttributes(), kName, key, vName, value);
		}
		return myDBDriver.executeCommand(cmd);
	}

	/**
	 * set the value of a key (column 1 and 2)
	 * 
	 * @param key
	 *            --> the key name
	 * @return the value of that key, or null on errors or if key name not found
	 */
	public int setKeyValue(String rowkey, String value) {
		return replaceOrInsertToken(getMyAttributes(), KEY_NAME, rowkey, VALUE_NAME, value);
	}

	/**
	 * To get a list of values from a table or sql-view
	 * 
	 * @param sql-query
	 * @return the list of values or null
	 */
	public ArrayList<String> getDataList(String query) {
		ArrayList<String> values = new ArrayList<String>();
		myDBDriver.executeQuery(query);
		while (myDBDriver.isThereAResult()) {
			values.add(myDBDriver.getFirstResultValue());
		}
		return values;
	}

	/**
	 * Methode, welche alle key.namen in einer Liste ausgibt
	 * 
	 * @return --> Retourniert die Liste mit allen key.namen werte
	 */
	public ArrayList<String> getDataList(Attribute key) {
		return getDataList(SQLHandler.selectCommand(getMyTableName(), key.getName(), null, null));
	}

	/**
	 * SETTERs and GETTERs
	 */
	public AttributeList getMyAttributes() {
		return myAttributes;
	}

	public String getMyTableName() {
		return myTableName;
	}

	public void setMyTableName(String myTableName) {
		this.myTableName = myTableName;
	}

	public PrimaryKey getPrimaryKey() {
		return myAttributes.getPrimaryKey();
	}

	public DBDriver getMyDBDriver() {
		return myDBDriver;
	}
}
