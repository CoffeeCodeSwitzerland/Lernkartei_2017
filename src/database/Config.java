package database;

import database.sql.Entity;

public class Config extends Entity {

	/**
	 * @param tabName
	 */
	public Config(String dbName, String tabName) {
		super(dbName, tabName, "PK_"+tabName);
		// TODO Auto-generated constructor stub
	}
}
