package database;
public class ImpAttributes {
	
	private String Name;
	private String Datatype;
	private Boolean NotNull;
	
	public ImpAttributes (String name, String datatype, boolean notNull) {
		
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
