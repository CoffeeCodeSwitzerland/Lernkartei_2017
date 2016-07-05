package database;

import database.jdbc.DBDriver;
import database.sql.Attribute;
import database.sql.Entity;
import database.sql.ForeignKey;

public class LearnEntity extends Entity {
	
	/**
	 * 
	 * @param tabName --> Name der Tabelle
	 */
	
	public LearnEntity(DBDriver dbDriver, String tabName) {
		super(dbDriver, tabName,"PK_"+tabName,false);
		// set table attributes
		Attribute a = new Attribute("WasCorrect",0);
		myAttributes.addUnique(a);
		a = new Attribute("Date");
		myAttributes.addUnique(a);
		ForeignKey f = new ForeignKey("PK_CARD");
		myAttributes.addUnique(f);
		f = new ForeignKey("PK_USER");
		myAttributes.addUnique(f);
		createTableIfNotExists();
	}
}
