package database.sql;

import database.sql.Attribute.Datatype;
import debug.Logger;

/**
 * Function pool to build SQL commands (may be static)
 * 
 * @author WISS
 *
 */
public abstract class SQLHandler {

	/**
	 * To build a where clause under different conditions
	 * 
	 * @param attributes
	 * @return the "WHERE..." clause or " ", Log error if no data for WHERE
	 */
	public static String toWhereClause(AttributeList attributes) {
		String whereClause = attributes.toClause("AND");
		if (!whereClause.equals("")) {
			return "WHERE " + whereClause;
		}
		Logger.out("insufficient data to build a WHERE-clause!");
		return "";
	}

	public static String toWhereClause(String name, String value, Datatype dType) {
		AttributeList attributes = new AttributeList();
		Attribute a = new Attribute(name, value, dType);
		attributes.add(a);
		return toWhereClause(attributes);
	}

	public static String toWhereClause(String pkeyName, String pkeyValue) {
		AttributeList attributes = new AttributeList();
		PrimaryKey pk = new PrimaryKey(pkeyName, pkeyValue);
		attributes.add(pk);
		return toWhereClause(attributes);
	}

	/**
	 * To create a table if it does not exist
	 * 
	 * @param tableName
	 * @param attributes
	 * @return the SQL command or "" for problems
	 */
	public static String createTableIfNotExistsCommand(String tableName, AttributeList attributes) {
		if (tableName != null && attributes != null && !tableName.equals("")) {
			String attrList = attributes.getCommaSeparatedList();
			if (!attrList.equals(""))
				return "CREATE TABLE IF NOT EXISTS " + tableName + " " + "(" + attrList + ")";
			else 
				Logger.out("empty data for update!", tableName);
		} else {
			Logger.out("invalid data for update!", tableName);
		}
		return null;
	}

	/**
	 * To delete one or more entries or all entries in a table
	 * 
	 * @param tableName
	 * @param keyName
	 *            (if null, will delete all entries)
	 * @param keyValue
	 * @param dType
	 * @return the SQL command or null for errors
	 */
	public static String deleteEntryCommand(String tableName, String keyName, String keyValue, Datatype dType) {
		//main.debug.Debugger.out("... trying to delete!");
		if (tableName != null && !tableName.equals("")) {
			//main.debug.Debugger.out("... trying to delete1! " + tableName);
			if (keyName != null) {
				//main.debug.Debugger.out("... trying to delete2! " + keyName);
				// there must be a correct where clause!
				String whereClause = toWhereClause(keyName, keyValue, dType);
				if (!whereClause.equals("")) {
					return "DELETE FROM " + tableName +" " + whereClause;
				} else {
					Logger.out("invalid data for a correct delete!", tableName);
				}
				return null;
			}
			//main.debug.Debugger.out("... trying to delete3! " + tableName);
			return "DELETE FROM " + tableName; // delete all only if
		} else {
			Logger.out("invalid table for delete!", tableName);
		}
		return null;
	}

	public static String deleteEntryCommand(String tableName, String pkeyName, String pkeyValue) {
		return deleteEntryCommand(tableName, pkeyName, pkeyValue, Datatype.PKEY);
	}

	public static String deleteEntryCommand(String tableName, String pkeyName, int pkeyValue) {
		return deleteEntryCommand(tableName, pkeyName, Integer.toString(pkeyValue), Datatype.PKEY);
	}

	/**
	 * To update values of an entry in a table
	 * 
	 * @param tabName
	 * @param attributes
	 * @return
	 */
	public static String updateInTableCommand(	String tabName, AttributeList attributes, 
												Attribute keyAttribute) {
		String updateCMD = "UPDATE " + tabName + " SET " + attributes.toClause(",");
		String clause = attributes.toValueList(false);
		if (!clause.equals("") && keyAttribute != null) {
			AttributeList plist = new AttributeList();
			plist.add(keyAttribute);
			updateCMD += toWhereClause(plist);
			return updateCMD;
		}
		Logger.out("invalid data to build an UPDATE!", tabName);
		return null;
	}

	public static String updateInTableCommand(String tabName, String name, String value, String pkeyName,
			String pkeyValue) {
		if (pkeyName != null && !pkeyName.equals("") && pkeyValue != null && !pkeyValue.equals("")) {
			AttributeList attributes = new AttributeList();
			Attribute pk = new Attribute(pkeyName, pkeyValue);
			Attribute a = new Attribute(name, value);
			attributes.add(a);
			return updateInTableCommand(tabName, attributes, pk);
		}
		Logger.out("invalud primary key or key value for the update");
		return "";
	}

	public static String updateInTableCommand(String tabName, String name, int value, String pkeyName,
			String pkeyValue) {
		if (pkeyName != null && !pkeyName.equals("") && pkeyValue != null && !pkeyValue.equals("")) {
			AttributeList attributes = new AttributeList();
			Attribute pk = new Attribute(pkeyName, pkeyValue);
			Attribute a = new Attribute(name, value);
			attributes.add(a);
			return updateInTableCommand(tabName, attributes, pk);
		}
		Logger.out("invalud primary key or key value for the update");
		return null;
	}

	/**
	 * To insert values into a table
	 * 
	 * @param tabName
	 * @param attributes
	 * @return
	 */
	public static String insertIntoTableCommand(String tabName, AttributeList attributes) {
		String attributeList = attributes.toKeyList(false);
		if (!attributeList.equals("") && tabName != null && !tabName.equals("")) {
			String insertCMD = "INSERT INTO " + tabName + " (" + attributeList + ") VALUES (";
			String valueList = attributes.toValueList(false);
			if (!valueList.equals("")) {
				insertCMD += valueList + ")";
				return insertCMD;
			}
			Logger.out("invalid data for SQL insert!", tabName);
		} else {
			Logger.out("invalid table or attributes for SQL insert!", tabName);
		}
		return null;
	}

	public static String insertIntoTableCommand(String tabName, AttributeList attributes, String att1, String val1, String att2, String val2) {
		Attribute a = new Attribute(att1, val1, Datatype.TEXT);
		attributes.add(a);
		a = new Attribute(att2, val2, Datatype.TEXT);
		attributes.add(a);
		return insertIntoTableCommand(tabName, attributes);
	}

	public static String insertIntoTableCommand(String tabName, String att1, String val1, String att2, String val2) {
		AttributeList attributes = new AttributeList();
		Attribute a = new Attribute(att1, val1, Datatype.TEXT);
		attributes.add(a);
		a = new Attribute(att2, val2, Datatype.TEXT);
		attributes.add(a);
		return insertIntoTableCommand(tabName, attributes);
	}

	/**
	 * To build a select from data
	 * 
	 * @param tabName
	 * @param attributes
	 *            (is optional)
	 * @return the SELCECT command or null for errors
	 */
	public static String selectCommand(String tabName, AttributeList attributes) {
		if (tabName != null && !tabName.equals("")) {
			// TODO handle cases with DISTINCT
			String attList = "*";
			if (attributes != null) {
				attList = attributes.getSeekedKeys();
				if (!(attList != null && !attList.equals(""))) attList ="*";
			}
			return "SELECT " +  attList + " FROM " + tabName;
		} else {
			Logger.out("invalid table name to build SQL select!", tabName);
		}
		return null;
	}

	public static String selectCommand(String tabName, AttributeList attributes, Attribute pk) {
		if (tabName != null && !tabName.equals("")) {
			// TODO handle cases with DISTINCT
			if (attributes != null) {
				String attList = attributes.toKeyList(true);
				if (!(attList != null && !attList.equals(""))) attList ="*";
				String selectCMD = "SELECT " + attList + " FROM " + tabName;
				if (pk != null) {
					AttributeList pklist = new AttributeList();
					pklist.add(pk);
					String whereClause = toWhereClause(pklist);
					if (!whereClause.equals(""))
						selectCMD += " " + whereClause;
				}
				return selectCMD;
			} else {
				String selectCMD = "SELECT * FROM " + tabName;
				if (pk != null) {
					AttributeList pklist = new AttributeList();
					pklist.add(pk);
					String whereClause = toWhereClause(pklist);
					if (!whereClause.equals(""))
						selectCMD += " " + whereClause;
				}
				return selectCMD;
			}
		} else {
			Logger.out("invalid table name to build SQL select!", tabName);
		}
		return null;
	}

	public static String selectCommand(String tabName, String seekKeyName, int seekKeyValue) {
		KeyAttribute a = new KeyAttribute(seekKeyName, seekKeyValue);
		return selectCommand(tabName, null, a);
	}

	public static String selectCommand(String tabName, String seekKeyName, String seekKeyValue) {
		KeyAttribute a = new KeyAttribute(seekKeyName, seekKeyValue, Datatype.TEXT);
		return selectCommand(tabName, null, a);
	}

	public static String selectCommand(String tabName, String returnKey, String seekKeyName, int seekKeyValue) {
		AttributeList aList = new AttributeList();
		if (returnKey != null) {
			Attribute ra = new Attribute (returnKey);
			aList.add(ra);
		} else aList = null;

		KeyAttribute a = new KeyAttribute(seekKeyName, seekKeyValue);
		return selectCommand(tabName, aList, a);
	}

	public static String selectCommand(String tabName, String returnKey, String seekKeyName, String seekKeyValue) {
		AttributeList aList = new AttributeList();
		if (returnKey != null) {
			Attribute ra = new Attribute (returnKey);
			aList.add(ra);
		} else aList = null;
		KeyAttribute a = new KeyAttribute(seekKeyName, seekKeyValue, Datatype.TEXT);
		return selectCommand(tabName, aList, a);
	}
}