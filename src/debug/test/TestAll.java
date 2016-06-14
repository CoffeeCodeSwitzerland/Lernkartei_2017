package debug.test;

import org.junit.Test;

public class TestAll {

	@Test
	public void test() {
		DebuggerTest.myTest();
		LoggerTest.myTest();
		SupervisorTest.myTest();
	}

}
