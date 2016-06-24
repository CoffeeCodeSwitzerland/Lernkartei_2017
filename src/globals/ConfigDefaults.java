package globals;

import java.util.HashMap;

public abstract class ConfigDefaults
{
	static boolean initialized = false;
	public static HashMap<Configurations, String[]> defaults = new HashMap<>();
	
	public enum Configurations
	{
		KEY
	}
	
	public static void ini ()
	{
		if (initialized)
			return;
		
		initialized = true;
		
		defaults.put(Configurations.KEY, toArray("Label", "Description", "DefaultValue"));
	}
	
	public static String[] getConfig (Configurations c)
	{
		return defaults.get(c);
	}
	
	private static String[] toArray (String... s)
	{
		return s;
	}
}
