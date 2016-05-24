package debug;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import globals.Environment;

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
public final class Logger {

	private final static ArrayList<String> logData = new ArrayList<String>(); // faster than logfile
	private static MyFile  myLogfile = null;

	private static boolean ramLoggingActive = false;
	private static boolean fileLoggingActive = true;

	private static boolean holdRamLoggingActive = false;
	private static boolean holdFileLoggingActive = true;

	private static long lastTime = 0L;
	
	public static void init () {
		if (myLogfile == null) {
			Debugger.out("Creating Logfile:'"+Environment.getActualPath()+Environment.getFileSep()+"LogfileOf"+Environment.getUserName()+".txt'");
			myLogfile = new MyFile("LogfileOf"+Environment.getUserName()+".txt");
			log("Start");
		}
	}
	
	
	public static void log(String logLine) {
		if (logLine == null)
			logLine = "{Logger:null?}";
		long time = LocalTime.now().toNanoOfDay();
		double diff = (time - lastTime)/100000000.0;
		lastTime = time;
		String stime = Long.toString(time).substring(1, 9);
		final DecimalFormat oneDigit = new DecimalFormat( "0.0" );
		String log = LocalDate.now() + "-" + stime +"<"+oneDigit.format( diff )+">"+ ": " + logLine;
		if (myLogfile == null) init();
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
		Logger.holdRamLoggingActive = ramLoggingActive;
	}

	public static void setFileLoggingActive(boolean fileLoggingActive) {
		Logger.fileLoggingActive = fileLoggingActive;
		Logger.holdFileLoggingActive = fileLoggingActive;
	}

	public static void stop () {
		Logger.holdFileLoggingActive = Logger.fileLoggingActive;
		Logger.fileLoggingActive = false;
		Logger.holdRamLoggingActive = Logger.ramLoggingActive;
		Logger.ramLoggingActive = false;
	}

	public static void restart () {
		Logger.fileLoggingActive = Logger.holdFileLoggingActive;
		Logger.ramLoggingActive = Logger.holdRamLoggingActive;
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
