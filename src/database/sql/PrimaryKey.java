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
	public boolean isPrimary() {
		return (getType() == Datatype.PKEY) ? true : false;
	}

	@Override
	public String getAttributeDefinition () {
		return getName() + " "+ SQLDataTypes[Datatype.PKEY.ordinal()];
	}
}
