package database.sql;

import debug.Logger;

/**
 * @author WISS
 *
 */
public class Attribute implements AttributeInterface {
	private String name; // SQL Attribute Name
	private String value; // may be an actual value or null if not set
	private int intValue; // same as value but already converted (to be fast)
	private String defaultValue; // may be a default value and null or "" if not
								 // set
	private Datatype type;

	public Attribute(String newName) {
		name = newName;
		value = "";
		intValue = 0;
		type = Datatype.TEXT;
		defaultValue = null;
	}

	public Attribute(String newName, String newValue) {
		name = newName;
		setValue(newValue);
		type = Datatype.TEXT;
		defaultValue = null;
	}

	public Attribute(String newName, String newValue, Datatype dType) {
		name = newName;
		setValue(newValue);
		type = dType;
		defaultValue = null;
	}

	public Attribute(String newName, int newValue) {
		this(newName, Integer.toString(newValue));
		if (value == null || value == "") {
			value = "0";
			intValue = 0;
		}
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

	public int getIntValue() {
		return intValue;
	}

	public void setValue(String newValue) {
		this.value = newValue;
		try {
			intValue = Integer.parseInt(newValue);
		} catch (Exception e) {
			intValue = 0;
		}
	}

	public void setValue(int value) {
		if (this.isTEXT()) {
			Logger.out("datatype changes are not allowed");
		} else {
			setValue(Integer.toString(value));
		}
	}

	/* (non-Javadoc)
	 * @see database.sql.AttributeInterface#getType()
	 */
	@Override
	public Datatype getType() {
		return type;
	}

	protected void setType(Datatype type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/* (non-Javadoc)
	 * @see database.sql.AttributeInterface#getAttributeDefinition()
	 */
	@Override
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
