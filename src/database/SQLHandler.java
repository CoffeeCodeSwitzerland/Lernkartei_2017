package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import debug.Logger;

public abstract class SQLHandler {

	protected static Statement stmt = null;

	protected static boolean createTableIfNotExists(String tableName, String attributes) {
		String sqlUpdate = "CREATE TABLE IF NOT EXISTS " + tableName + " " + "(" + attributes + ")";
		try {
			stmt.executeUpdate(sqlUpdate);
			debug.Debugger.out(sqlUpdate);
		} catch (Exception e) {
			if (stmt == null) {
				Logger.log("SQLHandler.createTableIfNotExists(...): open first!");
			}
			Logger.log("SQLHandler.createTableIfNotExists(" + sqlUpdate + ")");
			Logger.log("SQLHandler.createTableIfNotExists(" + tableName + "): " + e.getMessage());
			return false;
		}
		return true;
	}

	protected static ResultSet seekInTable(Connection c, String tabName, String attName, String pkey, String value) {
		String query = "SELECT " + attName + " FROM " + tabName + " WHERE " + pkey + " = " + value;
		try {
			c.setAutoCommit(false);
			ResultSet result = stmt.executeQuery(query);
			c.setAutoCommit(true);
			return result;
		} catch (Exception e) {
			if (stmt == null) {
				Logger.log("SQLHandler.seekInTable(...): open first!");
			}
			Logger.log("SQLHandler.seekInTable(" + query + ")");
			Logger.log("SQLHandler.seekInTable(..): " + e.getMessage());
		}
		try {
			c.setAutoCommit(true);
		} catch (Exception e) {};
		return null;
	}

	protected static ResultSet seekInTable (Connection c, String tabName, String attName, String value) {
		return seekInTable(c, tabName, attName, tabName, value);
	}

	protected static ResultSet updateInTable(String tabName, String attName, String value, String pkeyName,
			String pkeyValue) {
		String update = "UPDATE " + tabName + " SET " + attName + " = " + value + " WHERE " + pkeyName + " = "
				+ pkeyValue;
		try {
			stmt.executeUpdate(update);
		} catch (Exception e) {
			if (stmt == null) {
				Logger.log("SQLHandler.updateInTable(...): open first!");
			}
			Logger.log("SQLHandler.updateInTable(" + update + ")");
			Logger.log("SQLHandler.updateInTable(..): " + e.getMessage());
		}
		return null;
	}


}
