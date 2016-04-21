package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LoggerTest {

	public static void myTest() {
		Logger.setFileLoggingActive(true);
		assertEquals(true, Logger.isFileLoggingActive());
		Logger.setFileLoggingActive(false);
		assertEquals(false, Logger.isFileLoggingActive());
		Logger.setRamLoggingActive(false);
		assertEquals(false, Logger.isRamLoggingActive());
		Logger.setRamLoggingActive(true);
		assertEquals(true, Logger.isRamLoggingActive());
		// produce some logs in File and RAM
		Logger.setFileLoggingActive(true);
		Logger.setRamLoggingActive(true);
		Logger.log("Testfehler 1");
		Logger.log("Testfehler 2");
		// Chekc if there are 2 RAM logs
		assertEquals(2, Logger.printFullLog());
		// check if file exists
		Debugger.out("Logfile: " + Logger.getMylogfile().getTargetInformation());
		assertEquals(true, new MyFile(Logger.getMylogfile().getTargetInformation()).isFile());
	}

	@Test
	public void test() {
		myTest();
		Debugger.eol();
		Debugger.title("Further manual tests are needed:");
		System.out.println("- on Eclipse: select the 'Lernkartei' project and press F5 to see the logfile");
		System.out.println("- check the logfile: new entries are always appended with time-stamps!");
		System.out.println("- to 'reset' the logs, delete the logfile manually!");
	}

}
