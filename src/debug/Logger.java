package debug;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import globals.Environment;

/**
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
	private static Path  myLogfile = null;

	private static boolean ramLoggingActive = false;
	private static boolean fileLoggingActive = true;

	private static boolean holdRamLoggingActive = false;
	private static boolean holdFileLoggingActive = true;

	private static long lastTime = 0L;
	private static boolean appendActive = true; // =false, to overwrite older file
	
	public static void init () {
		if (myLogfile == null) {
		    myLogfile = Paths.get(Environment.getDatabaseLocation()+Environment.getFileSep()+"LogfileOf"+Environment.getUserName()+".txt");
		    try {
				Files.createDirectories(myLogfile.getParent());			
				Files.createFile(myLogfile);
			} catch (Exception e) {
				log("File creation problem!");
			}
			log("Start");
		}
	}

	public static void save(String logLine) {
		File f = myLogfile.toFile();
		RandomAccessFile myFile = null;
		try {
			//f = new File(myLogfile.toFile().getName());
			long fileLength = f.length();
			myFile = new RandomAccessFile(f, "rw");

			// TODO control log size, if too big overwrite it automatically

			if (appendActive) {
				myFile.seek(fileLength); // add this line to append data, else
											// it overwrites lines
			} else {
				// wrong: appendActive = true; // activate append after first
				// line automaticly
			}
			myFile.writeBytes(logLine + Environment.getEndOfLine()); // .writeChars()
																// writes UTF16
																// Chars!

			myFile.close();
		} catch (IOException ex1) {
			System.err.println("Supervisor: Cannot handle the log-file '" + myLogfile.toFile().getName() + "'!");
			if (myFile != null) {
				try {
					myFile.close();
				} catch (IOException ex2) {
				}
			}
		}
	}

	public void setAppendActive(boolean doNotOverwriteOldOne) {
		appendActive = doNotOverwriteOldOne;
	}

	
	public static void log(String logLine) {
		if (logLine == null)
			logLine = "{Logger:null?}";
		long time = LocalTime.now().toNanoOfDay();
		double diff = 0.0;
		if (lastTime > 0L) {
			diff = (time - lastTime) / 100000000.0;
		}
		lastTime = time;
		String stime = Long.toString(time).substring(1, 9);
		final DecimalFormat oneDigit = new DecimalFormat( "0.0" );
		String log = LocalDate.now() + "-" + stime +"<"+oneDigit.format( diff )+">"+ ": " + logLine;
		if (myLogfile == null) init();
		if (myLogfile != null && fileLoggingActive == true) {
			save(log);
		}
		if (ramLoggingActive) {
			logData.add(log);
		}
	}

	private static String getPrefix () {
		return ">>> "+
				Thread.currentThread().getStackTrace()[3].getClassName()+"."+
				Thread.currentThread().getStackTrace()[3].getMethodName()+"->"+
				Thread.currentThread().getStackTrace()[2].getClassName()+"."+
				Thread.currentThread().getStackTrace()[2].getMethodName()+": ";
	}

	public static void log(String errType, Object callingObject, String logLine) {
		String logPrefix = errType +"-"+getPrefix();
		if (callingObject == null) {
			if (logLine == null) {
				Logger.log(logPrefix + "... at Nullpointer!");
			} else {
				Logger.log(logPrefix + " '" + logLine + "'... at Nullpointer!");
			}
		} else {
			if (logLine == null) {
				Logger.log(logPrefix + "... at " + callingObject.toString());
			} else {
				Logger.log(logPrefix + " " + logLine + "' at " + callingObject.toString() + "!");
			}
		}
	}

	public static void log(Exception e, String logLine) {
		String logPrefix = ">Exception-"+getPrefix();
		if (e == null) {
			if (logLine == null) {
				Logger.log(logPrefix + "... (no text)!");
			} else {
				Logger.log(logPrefix + " '" + logLine + "'... with Nullpointer !?!");
			}
		} else {
			if (logLine == null) {
				Logger.log(logPrefix + "... at ");
			} else {
				Logger.log(logPrefix + " " + logLine + "' at " + "!");
			}
			e.printStackTrace();
		}
	}

	public static void out(String debugText, int calls, String param1, String param2) {
		if (debugText == null) debugText = ""; 
		String debugPrefix ="";
		for (int i=calls+1; i>=3; i--) {
			if (i < calls+1) debugPrefix += "->"; 
			debugPrefix += 
					Thread.currentThread().getStackTrace()[i].getClassName()+"."+
					Thread.currentThread().getStackTrace()[i].getMethodName();
			if ((calls+2-i) % 2 == 0 && i != 3) debugPrefix += "\n";
		}
		String p = "(";
		if (param1 != null) p += param1;
		if (param2 != null) {
			if (param1 != null) p+=",";
			p+=param2;
		}
		p+=")";
		String spacing ="  ";
		for (int i=0; i < 54-debugText.length();i++) spacing +=" ";
		debug.Debugger.out(debugText +spacing+"{"+debugPrefix+p+"}", param1, param2);
		log(debugText);
	}

	public static void out(String debugText, String param1, String param2) {
		if (param1 == null) param1 = "{null}";
		if (param2 == null) param2 = "{null}";
		out (debugText, 2, param1, param2);
	}

	public static void out(String debugText, String param1, int param2) {
		if (param1 == null) param1 = "{null}";
		out (debugText, 2, param1, Integer.toString(param2));
	}

	public static void out(String debugText, int param1, int param2) {
		out (debugText, 2, Integer.toString(param1), Integer.toString(param2));
	}

	public static void out(String debugText, String param) {
		if (param == null) param = "{null}";
		out (debugText, 2, param, null);
	}

	public static void out(String debugText, int calls) {
		out (debugText, calls, null, null);
	}

	public static void out(String debugText) {
		out (debugText, 2, null, null);
	}
	
	public static void out(Exception e) {
		out (e.getMessage(), 2, null, null);
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

//	public static MyFile getMylogfile() {
//		return myLogfile;
//	}
}
