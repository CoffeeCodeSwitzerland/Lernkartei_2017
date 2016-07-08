package globals;

import java.util.HashMap;

public abstract class ConfigDefaults
{
	static boolean initialized = false;
	static String f = "false";
	static String t = "true";
	public static HashMap<Configurations, String[]> defaults = new HashMap<>();
	
	public enum Configurations
	{
		withState,
		hideImageStacks,
		tooltipp
	}
	
	public static void ini ()
	{
		if (initialized)
			return;
		
		initialized = true;
		
		// This is the order of the options in the options menu
		// defaults.put(Configurations.KEY, toArray("Label", "Description", "DefaultValue"));
		defaults.put(Configurations.withState, toArray("Grösse Anpassen",
				"Wenn aktiviert, werden alle Buttons dem Grössten angepasst. Sonst orientiert sich die Grösse jeweils am Namen des Buttons.", f));
		defaults.put(Configurations.hideImageStacks, toArray("Nur Stapel ohne Bilder",
				"Zeige nur Stapel, die keine Bilder enthalten", t));
		defaults.put(Configurations.tooltipp, toArray("Tooltipps deaktivieren",
				"Deaktiviere Tooltipps. Wenn diese Option aktiviert ist, werden keine Tooltipps angezeigt.", f));
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
