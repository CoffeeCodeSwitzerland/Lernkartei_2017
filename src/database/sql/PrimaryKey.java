package database.sql;

/**
 * @author WISS
 *
 */
public class PrimaryKey extends Attribute {

	public PrimaryKey (String newName) {
		super (newName);
		setType(Datatype.PKEY);
	}

	public PrimaryKey (String newName, String value) {
		super (newName, value);
		setType(Datatype.PKEY);
	}

	@Override
	public boolean isKey() {
		return true;
	}

	@Override
	public boolean isPrimary() {
		return true;
	}

	@Override
	public boolean isValue() {
		return true;
	}

	@Override
	public String getAttributeDefinition () {
		return getName() + " "+ SQLDataTypes[Datatype.PKEY.ordinal()];
	}
}
