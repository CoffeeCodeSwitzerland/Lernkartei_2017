package debug;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public final class Logger {

	/*-
	 * Purpose:	
	 * - to simplify logging (handles a log-file and an ArrayList)
	 * - you may log in RAM (fast method) and/or
	 * - you may log on a log-file (slower but appending log's)
	 * - if too big, delete the log-file manually
	 * - time stamps are added automatically
	 * 
	 * @AUTHOR Hugo Lucca
	 */

	private final static ArrayList<String> logData = new ArrayList<String>(); // faster
	private final static MyFile myLogfile = new MyFile("logfile.txt"); // slower

	private static boolean ramLoggingActive = false;
	private static boolean fileLoggingActive = true;

	public static void log(String logLine) {
		if (logLine == null)
			logLine = "{Logger:null?}";
		String log = LocalDate.now() + "-" + LocalTime.now().toNanoOfDay() + ": " + logLine;
		if (myLogfile != null && fileLoggingActive == true) {
			myLogfile.save(log);
		}
		if (ramLoggingActive) {
			logData.add(log);
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

	public static MyFile getMylogfile() {
		return myLogfile;
	}
}
