package database.sql;

/**
 * @author WISS
 *
 */
public class Attribute {
	public enum Datatype {
		PKEY, FKEY, INT, TEXT, DATE, TIME
	}
	final protected static String SQLDataTypes[] = { "INTEGER PRIMARY KEY AUTOINCREMENT", "INTEGER FOREIGN KEY",
			"INTEGER", "TEXT", "DATE", "TIME" }; // !!! same len and order as Datatype!

	private String name; // SQL Attribute Name
	private String value; // may be an actual value or null if not set
	private String defaultValue; // may be a default value and null or "" if not
								 // set
	private Datatype type;

	public Attribute(String newName) {
		name = newName;
		value = "";
		type = Datatype.TEXT;
		defaultValue = null;
	}

	public Attribute(String newName, String newValue) {
		name = newName;
		value = newValue;
		type = Datatype.TEXT;
		defaultValue = null;
	}

	public Attribute(String newName, String newValue, Datatype dType) {
		name = newName;
		value = newValue;
		type = dType;
		defaultValue = null;
	}

	public Attribute(String newName, int newValue) {
		this(newName, Integer.toString(newValue));
		if (value == null) value = "0";
		if (value == "") value = "0";
		type = Datatype.INT;
	}

	public Attribute(String newName, int newValue, String newDefault) {
		this(newName, newValue);
		defaultValue = newDefault;
	}

	public Attribute(String newName, String newValue, String newDefault) {
		this(newName, newValue);
		defaultValue = newDefault;
	}

//	public Attribute(String newName, int newValue, int newDefault) {
//		this(newName, newValue);
//		defaultValue = Integer.toString(newDefault);
//	}

	public String getName() {
		return name;
	}

	public boolean isValue() {
		if (type == Datatype.INT)
			return true;
		if (type == Datatype.PKEY)
			return true;
		if (type == Datatype.FKEY)
			return true;
		return false;
	}

	public boolean isTEXT() {
		if (type == Datatype.TEXT)
			return true;
		if (type == Datatype.DATE)
			return true;
		if (type == Datatype.TIME)
			return true;
		return false;
	}

	public boolean isForeign() {
		return false;
	}

	public boolean isPrimary() {
		return false;
	}

	public boolean isKey() {
		return false;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Datatype getType() {
		return type;
	}

	public void setType(Datatype type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getAttributeDefinition() {
		String def = name + " ";
		if (type == Datatype.INT) {
			def += SQLDataTypes[Datatype.INT.ordinal()];
		} else {
			def += SQLDataTypes[Datatype.TEXT.ordinal()];
		}
		if (defaultValue != null)
			def += " DEFAULT " + defaultValue;
		return def;
	}
}
