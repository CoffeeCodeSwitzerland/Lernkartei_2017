package globals;

import java.util.HashMap;

public abstract class ConfigDefaults
{
	boolean initialized = false;
	public HashMap<Configurations, String[]> defaults = new HashMap<>();
	
	public enum Configurations
	{
		KEY
	}
	
	public void ini ()
	{
		initialized = true;
		
		defaults.put(Configurations.KEY, toArray("Label", "Description", "DefaultValue"));
	}
	
	private String[] toArray (String... s)
	{
		return s;
	}
}
