package globals;

import java.io.File;
import java.nio.file.Paths;

public final class Environment {

	/*-
	 * Purpose:	
	 * - to simplify environment variable access (system properties)
	 * - you may get file-separator, endOfLine-char, user-path's etc. 
	 * 
	 * @AUTHOR Hugo Lucca
	 */

	private static String fileSep = null;
	private static String endOfLine = null;

	private static String classPath = null;
	private static String actualPath = null;
	private static String homePath = null;
	private static String userPath = null;
	private static String userName = null;

	/*-    (this squences deactivates the comment formatter!)
	 * 
	 * ============================
	 * How to get System Properties:
	 * ============================
	 * Key 					Meaning
	 * ---					-------
	 * "file.separator"		Character that separates components of a file
	 * 						path. This is "/" on UNIX and "\" on Windows.
	 * "java.class.path"	Path used to find directories and JAR archives
	 * 						containing class files. Elements of the class 
	 * 						path are separated by a platform-specific 
	 * 						character specified in the path.separator property.
	 * "java.home"			Installation directory for the JRE
	 * "java.vendor"		JRE vendor name "java.vendor.url" JRE vendor URL
	 * "java.version"		JRE version number "line.separator" Sequence used
	 * 						by the operating system to separate lines in text
	 * 						files
	 * "os.arch"			Operating system architecture
	 * "os.name"			Operating system name
	 * "os.version"			Operating system version
	 * "path.separator"		Path separator character used in java.class.path
	 * "user.dir"			User working directory
	 * "user.home"			User home directory "user.name" User account name
	 */

	public static String getUserName() {
		// geht evtl. nur bei Windows, 
		// ansonsten TODO nimm getPath und extract Username
		if (homePath == null) init();
		return userName;
	}

	public static void init() {
		homePath = System.getProperty("user.dir");
		userPath = System.getProperty("user.home");
		userName = System.getenv().get("USERNAME");  // evtl. nur für Windows
		fileSep = System.getProperty("file.separator");
		classPath = System.getProperty("java.class.path");
		endOfLine = System.getProperty("line.separator");
		actualPath = Paths.get(".").toAbsolutePath().normalize().toString();
	}

	public static String getHomePath() {
		if (homePath == null) init();
		return homePath;
	}

	public static String getUserPath() {
		if (homePath == null) init();
		return userPath;
	}

	public static String getFileSep() {
		if (homePath == null) init();
		return fileSep;
	}

	public static String getClassPath() {
		if (homePath == null) init();
		return classPath;
	}

	public static String getEndOfLine() {
		if (homePath == null) init();
		return endOfLine;
	}

	public static String getActualPath() {
		if (homePath == null) init();
		return actualPath;
	}
	
	public static String getDatabasePath() {
		if (fileSep == null) init();
		File theDir = new File(System.getenv("APPDATA") + fileSep + Globals.db_Path);
		if (!theDir.exists()) {
			theDir.mkdirs();
		}
		
		String path = System.getenv("APPDATA") + fileSep + Globals.db_Path + fileSep;
		return path;
	}
	
	public static String getDatabaseLocation() {
		if (fileSep == null) init();
		File theDir = new File(System.getenv("APPDATA") + fileSep + Globals.db_Path);
		if (!theDir.exists()) {
			theDir.mkdirs();
		}
		
		String path = System.getenv("APPDATA") + fileSep + Globals.db_Path;
//		debug.Debugger.out("Environement.getDatabaseLocation: "+path);
		return path;
	}
}
