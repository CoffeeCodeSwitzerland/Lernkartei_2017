package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LoggerTest {
	
	public static void myTest () {
		Logger.setFileLoggingActive(true);
		assertEquals(true, Logger.isFileLoggingActive());
		Logger.setFileLoggingActive(false);
		assertEquals(false, Logger.isFileLoggingActive());
		Logger.setRamLoggingActive(false);
		assertEquals(false, Logger.isRamLoggingActive());
		Logger.setRamLoggingActive(true);
		assertEquals(true, Logger.isRamLoggingActive());
	}

	@Test
	public void test() {
		LoggerTest.myTest();
	}

}
