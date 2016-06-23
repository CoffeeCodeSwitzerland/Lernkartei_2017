package database.sql;

/**
 * @author WISS
 *
 */
public class ForeignKey extends Attribute {

	public ForeignKey (String newName) {
		super (newName,0);
		setType(Datatype.FKEY);
	}
	
	public ForeignKey (String newName, String value) {
		super (newName, value);
		setType(Datatype.FKEY);
	}
	
	@Override
	public boolean isForeign() {
		return (getType() == Datatype.FKEY) ? true : false;
	}

	@Override
	public boolean isValue() {
		return true;
	}

	@Override
	public boolean isKey() {
		return true;
	}

	@Override
	public String getAttributeDefinition () {
		return getName() + " "+ SQLDataTypes[Datatype.PKEY.ordinal()];
	}
}
