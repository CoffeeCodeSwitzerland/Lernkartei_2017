package database;

import java.sql.Statement;

import debug.Logger;

public abstract class SQLHandler {

	protected static Statement stmt = null;
	
	protected static boolean createTableIfNotExists(String tableName, String attributes) {
		String sqlCreate = "CREATE TABLE IF NOT EXISTS " + tableName + " " + "(" + attributes + ");";
		try {
			stmt.executeUpdate(sqlCreate);
//			debug.Debugger.out(sqlUpdate);
		} catch (Exception e) {
			if (stmt == null) {
				Logger.log("SQLHandler.createTableIfNotExists(...): open first!");
			}
			Logger.log("SQLHandler.createTableIfNotExists(" + sqlCreate + ")");
			Logger.log("SQLHandler.createTableIfNotExists(" + tableName + "): " + e.getMessage());
			debug.Debugger.out("SQLHandler.createTableIfNotExists(" + sqlCreate + ")");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param sqlStatement
	 * @return 0, row count or -1 for error
	 */
	protected static int updateSQL (String sqlStatement) {
		try {
			return stmt.executeUpdate(sqlStatement);
		} catch (Exception e) {
			if (stmt == null) {
				Logger.log("SQLHandler.updateInTable(...): open first!");
			}
			debug.Debugger.out("SQLHandler.updateInTable(" + sqlStatement + ")");
			Logger.log("SQLHandler.updateInTable(" + sqlStatement + ")");
			Logger.log("SQLHandler.updateInTable(..): " + e.getMessage());
		}
		return -1;
	}

	protected static int updateInTable(String tabName, String att1Name, String value1, 
							String att2Name, String value2, String pkeyName, String pkeyValue) {
		
		return updateSQL( "UPDATE " + tabName + " SET " 
						+ att1Name + " = '" + value1 + "'"
						+ att2Name + " = '" + value2 + "'"
						+" WHERE " + pkeyName + " = '" + pkeyValue+"'");
	}
	
	protected static int updateInTable(	String tabName, String attName, String value, String pkeyName,
										String pkeyValue) {
		return updateSQL( "UPDATE " + tabName + " SET " 
						+ attName + " = '" + value + "'" 
						+ " WHERE " + pkeyName + " = '"+ pkeyValue+"'");
	}
	
	protected static int insertSQL (String sqlStatement) {
		try {
			return stmt.executeUpdate(sqlStatement);
		} catch (Exception e) {
			if (stmt == null) {
				Logger.log("SQLHandler.insertSQL(...): open first!");
			}
			debug.Debugger.out("SQLHandler.insertSQL(" + sqlStatement + ")");
			Logger.log("SQLHandler.insertSQL(" + sqlStatement + ")");
			Logger.log("SQLHandler.insertSQL(..): " + e.getMessage());
		}
		return -1;
	}
	
	protected static int insertIntoTable (String tabName, String attributes, String FK_ID, String[] values) {
		String sqlStatement = "INSERT INTO Stock ("+attributes+") VALUES ('" + FK_ID;
		for (int i=0; i< values.length; i++) {
			sqlStatement += "','" + values[i];
		}
		sqlStatement += "')";
		return insertSQL (sqlStatement);
	}
	
	protected static int insertIntoTable (String tabName, String attributes, String[] values) {
		String sqlStatement = "INSERT INTO Stock ("+attributes+") VALUES ('" + values[0];
		for (int i=1; i< values.length; i++) {
			sqlStatement += "','" + values[i];
		}
		sqlStatement += "')";
		return insertSQL (sqlStatement);
	}
	
	/**
	 * 
	 * @param sqlStatement
	 * @return 0, row count or -1 for error
	 */
	protected static boolean deleteSQL (String tabName, String pkName, String substring) {
		boolean deleted = false;
		String del = "DELETE FROM "+tabName+" WHERE "+pkName+" = " + substring;
		try {
			stmt.executeUpdate(del);
			deleted = true;
		}
		catch (Exception e) {
			debug.Debugger.out("SQLHandler.deleteSQL("+del+"): "+e.getMessage());
			Logger.log("SQLHandler.deleteSQL("+del+"): "+e.getMessage());
		}
		return deleted;
	}
	
	protected static boolean deleteSQL (String tabName, String pkName, Integer id) {
		return deleteSQL (tabName, pkName, "'" + Integer.toString(id) +"'");
	}
}
