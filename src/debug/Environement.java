package debug;

import java.nio.file.Paths;

public class Environement {

	private String fileSep;
	private String endOfLine;

	private String classPath;
	private String actualPath;
	private String homePath;  
	
	/** How to get System Properties:
	 * 	Key 				Meaning
		"file.separator" 	Character that separates components of a file path. This is "/" on UNIX and "\" on Windows.
		"java.class.path" 	Path used to find directories and JAR archives containing class files. Elements of the class path are separated by a platform-specific character specified in the path.separator property.
		"java.home" 		Installation directory for Java Runtime Environment (JRE)
		"java.vendor" 		JRE vendor name
		"java.vendor.url" 	JRE vendor URL
		"java.version" 		JRE version number
		"line.separator" 	Sequence used by operating system to separate lines in text files
		"os.arch" 			Operating system architecture
		"os.name" 			Operating system name
		"os.version" 		Operating system version
		"path.separator" 	Path separator character used in java.class.path
		"user.dir" 			User working directory
		"user.home" 		User home directory
		"user.name" 		User account name
	 */
	public Environement () {
		homePath  = System.getProperty("user.dir");
		fileSep   = System.getProperty("file.separator");
		classPath = System.getProperty("java.class.path");
		endOfLine = System.getProperty("line.separator");
		actualPath = Paths.get(".").toAbsolutePath().normalize().toString();

		Debugger.out("Home:"+homePath);
		Debugger.out("Class:"+classPath);
		Debugger.out("Actual:"+actualPath);
	}

	public String getHomePath() {
		return homePath;
	}

	public String getFileSep() {
		return fileSep;
	}

	public String getClassPath() {
		return classPath;
	}

	public String getEndOfLine() {
		return endOfLine;
	}

	public String getActualPath() {
		return actualPath;
	}
}
