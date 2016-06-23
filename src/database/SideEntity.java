package database;

import database.sql.Attribute;
import database.sql.Entity;

public class SideEntity extends Entity {

	/**
	 * @param tabName
	 */
	public SideEntity(String tabName) {
		super(tabName, tabName+"_PK");
		// set table attributes
		Attribute a = new Attribute("Link");
		myAttributes.add(a);
		a = new Attribute("BackgoundColor");
		myAttributes.add(a);
		a = new Attribute("Text");
		myAttributes.add(a);
		a = new Attribute("Order",0);
		myAttributes.add(a);
		createTableIfNotExists();
	}

}
