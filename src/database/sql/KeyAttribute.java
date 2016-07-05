package database.sql;

/**
 * @author WISS
 *
 */
public class KeyAttribute extends Attribute {

	public KeyAttribute (String newName, String newValue, Datatype keyType) {
		super (newName, newValue);
		setType(keyType);
	}
	
	public KeyAttribute (String newName, int newValue) {
		super (newName, newValue);
	}
	
	public KeyAttribute (String newName, int newValue, String newDefault) {
		super (newName, newValue, newDefault);
	}
	
	public KeyAttribute (String newName, String newValue, Datatype keyType, String newDefault) {
		super (newName, newValue, newDefault);
		setType(keyType);
	}
	
	@Override
	public boolean isKey() {
		return true;
	}

	@Override
	public String getAttributeDefinition () {
		String def = getName() + " ";
		if (getType() == Datatype.INT) {
			def += SQLDataTypes[Datatype.UINT.ordinal()];
		} else {
			def += SQLDataTypes[Datatype.UTEXT.ordinal()];
		}
		def += " NOT NULL";
		if (getDefaultValue() != null) def += " DEFAULT " + getDefaultValue();
		return def;
	}
}
