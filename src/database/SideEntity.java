package database;

import database.jdbc.DBDriver;
import database.sql.Attribute;
import database.sql.Entity;

public class SideEntity extends Entity {

	/**
	 * @param tabName
	 */
	public SideEntity(DBDriver dbDriver, String tabName) {
		super(dbDriver, tabName, "PK_"+tabName,false);
		// set table attributes
		Attribute a = new Attribute("Link");
		myAttributes.addUnique(a);
		a = new Attribute("BackgoundColor");
		myAttributes.addUnique(a);
		a = new Attribute("Text");
		myAttributes.addUnique(a);
		a = new Attribute("SideOrder",0);
		myAttributes.addUnique(a);
		createTableIfNotExists();
	}

}
