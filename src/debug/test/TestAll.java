package debug.test;

import org.junit.Test;

public class TestAll {

	@Test
	public void test() {
		DebuggerTest.myTest();
		EnvironementTest.myTest();
		LoggerTest.myTest();
		SupervisorTest.myTest();
	}

}
