package debug;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import globals.Environment;

public class MyFile extends File {

	private static final long serialVersionUID = 1L;
	/**
	 * Vorbereiteter Sourcecode für die korrekte Handhabung einer Datei mit
	 * Berücksichtigung von Environement Variablen, Pfade etc.
	 * 
	 * Stand V1.0: - für random access und logfile spezialisiert (kein Problem
	 * mit close)
	 * 
	 * @AUTHOR Hugo Lucca
	 */
	private boolean appendActive = true; // =false, to overwrite older file
											// (default: true)
	public MyFile(String pathAndFilename) {
		super(pathAndFilename);
	}

	public void save(String logLine) {
		File f = null;
		RandomAccessFile myFile = null;
		try {
			f = new File(Environment.getActualPath() + Environment.getFileSep() + this.getName());
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
			System.err.println("Supervisor: Cannot handle the log-file '" + this.getName() + "'!");
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

//	public String getTargetInformation() {
//		return Environment.getActualPath() + Environment.getFileSep() + this.getName();
//	}

}
