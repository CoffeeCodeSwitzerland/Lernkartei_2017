package datenbank;

public class UserAttributes {
	
	private String Name;
	private String Datatype;
	private Boolean NotNull;
	
	public UserAttributes (String name, String datatype, boolean notNull) {
		
		Name = name;
		Datatype = datatype;
		NotNull = notNull;
		
	}

	public String getName() {
		return Name;
	}

	public String getDatatype() {
		return Datatype;
	}

	public Boolean getNotNull() {
		return NotNull;
	}
	
}
