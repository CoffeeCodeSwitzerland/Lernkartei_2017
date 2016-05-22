package globals;

/**
 * Contains centralized Globals that are used for this application 
 * 
 * @author hugo-lucca
 */
public abstract class Globals {

	public static String userLang = "CH-D"; // will be used in future translator

	public static final String appTitle = "WISSLearnCards 2016";
	public static final String appVersion = "[V0.1 alpha]";

	public static final String SEPARATOR = ":::";
	
	public static final String db_Path = "WISS_Learncards_db";
	public static final String db_name = "Lernkarten_db";

	public static final String CSSExtention = ".css";
	public static final String stylesSupPath = "styles";
	public static final String mainStyleFileName = "style";
	
	public static final String[] evenTags = new String[]{"u", "s", "sup", "sub"};
	public static final String[] pairedTags = new String[]{"b", "strong", "i", "em"};
	
	public static final int defaultStackPartSize = 20;
	
	public static final boolean DebuggerIsOn = true;
	public static final boolean TestingIsOn  = true;
}
