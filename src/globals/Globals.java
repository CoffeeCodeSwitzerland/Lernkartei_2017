package globals;

/**
 * Contains centralized Globals that are used for this application 
 * 
 * @author hugo-lucca
 */
public abstract class Globals
{
	public static String userLang = "CH-D"; // will be used in future translator

	public static final String appTitle = "WISSLearnCards 2016";
	public static final String appVersion = "[V0.1 alpha]";

	public static final String SEPARATOR = ":::";
	
	public static final String db_Path = "WISS_Learncards_db";
	public static final String db_name = "Lernkarten";
	public static final String config_db_name = "config";

	public static final String CSSExtention = ".css";
	public static final String stylesSupPath = "styles";
	public static final String mainStyleFileName = "style";
	
	public static final String[] evenTags = new String[]{"u", "s", "sup", "sub"};
	public static final String[] pairedTags = new String[]{"b", "strong", "i", "em"};
	public static final String[] complexTags = new String[]{"color", "img"};
	
	public static final int defaultStackPartSize = 20;
	public static final int minStackPartSize = 5;
	public static final int maxStackPartSize = 1000;
	
	
	public static final int defaultScrollerWidth = 400;
	

		
	// The logger should always remain active, to track user problems.
	// The log-file is stored at the same place of both databases (LK and config)
	public static final boolean LoggerIsOn 	  = true;  // Logger ON/OFF (should remain ON)
	
	// Do any change manually here:
	// - the debugger may be on while software development, but not needed as runnable JAR version: 
	public static final boolean DebuggerIsOn = true; // Debugger ON/OFF (deactivate before release)
	// - to make tests easier: 
	public static final boolean TestingIsOn  = true; // TestConditions ON/OFF (deactivate before release)

	// To simplify structural changes of the data base
	// but the user will lose his cards. 
	// TODO to avoid this own card's exports should be possible (to XML, CSV or other DB)
	// Do any change manually here:
	// TODO is a future expansion
	public static final boolean ForceNewDB    = false; // activate to delete old db (not the config!)
	// ... and this value should be saved in config.db and updated after an old LK-db delete+rebuild
	public static final String ForDBVersionLT = "1.0"; // increase this to delete only the older DB's
}

