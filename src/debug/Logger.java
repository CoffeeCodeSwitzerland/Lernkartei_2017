package debug;

import java.util.ArrayList;

public final class Logger {

	private final static ArrayList<String> logData = new ArrayList<String>(); // faster

	public static Logfile getMylogfile() {
		return myLogfile;
	}

	private final static Logfile myLogfile = new Logfile("logfile.txt"); // slower

	private static boolean ramLoggingActive = true;
	private static boolean fileLoggingActive = false;

	public static void log(String logLine) {
		if (myLogfile != null && fileLoggingActive == true) {
			myLogfile.save(logLine);
		}
		if (ramLoggingActive) {
			logData.add(logLine);
		}
	}

	public static void log(String errType, Object callingObject, String logLine) {
		if (callingObject == null) {
			if (logLine == null) {
				Logger.log(">>>" + errType + "... at Nullpointer!");
			} else {
				Logger.log(">>>" + errType + ": '" + logLine + "'... at Nullpointer!");
			}
		} else {
			if (logLine == null) {
				Logger.log(">>>" + errType + "... at " + callingObject.toString());
			} else {
				Logger.log(">>>" + errType + ": " + logLine + "' at " + callingObject.toString() + "!");
			}
		}
	}

	public static void log(Exception e, String logLine) {
		String errType = ">Exception";
		if (e == null) {
			if (logLine == null) {
				Logger.log(errType + "... (no text)!");
			} else {
				Logger.log(errType + ": '" + logLine + "'... with Nullpointer !?!");
			}
		} else {
			if (logLine == null) {
				Logger.log(errType + "... at ");
			} else {
				Logger.log(errType + ": " + logLine + "' at " + "!");
			}
			e.printStackTrace();
		}
	}

	public static int printFullLog() {
		for (int i = 0; i < logData.size(); i++) {
			System.out.println(logData.get(i));
		}
		return logData.size();
	}

	public static void setRamLoggingActive(boolean ramLoggingActive) {
		Logger.ramLoggingActive = ramLoggingActive;
	}

	public static void setFileLoggingActive(boolean fileLoggingActive) {
		Logger.fileLoggingActive = fileLoggingActive;
	}

	public static boolean isRamLoggingActive() {
		return ramLoggingActive;
	}

	public static boolean isFileLoggingActive() {
		return fileLoggingActive;
	}
}
