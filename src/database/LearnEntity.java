package database;

import database.sql.Attribute;
import database.sql.Entity;
import database.sql.ForeignKey;

public class LearnEntity extends Entity {
	
	/**
	 * 
	 * @param tabName --> Name der Tabelle
	 */
	
	public LearnEntity(String tabName) {
		super(tabName,"PK_"+tabName);
		// set table attributes
		Attribute a = new Attribute("WasCorrect",0);
		myAttributes.add(a);
		a = new Attribute("Date");
		myAttributes.add(a);
		ForeignKey f = new ForeignKey("PK_CARD");
		myAttributes.add(f);
		f = new ForeignKey("PK_USER");
		myAttributes.add(f);
		createTableIfNotExists();
	}
}
