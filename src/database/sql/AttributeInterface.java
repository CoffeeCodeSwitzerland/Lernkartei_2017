package database.sql;

/**
 * @author WISS
 *
 */
public interface AttributeInterface {
	public enum Datatype {
		PKEY, FKEY, INT, TEXT, DATE, TIME, UINT, UTEXT
	}
	final static String SQLDataTypes[] = { "INTEGER PRIMARY KEY AUTOINCREMENT", "INTEGER",
			"INTEGER", "TEXT", "DATE", "TIME", "INTEGER UNIQUE", "TEXT UNIQUE" }; 
			// !!! implement same length and order as in the Datatype definition!


	Datatype getType();

	String getAttributeDefinition();

}