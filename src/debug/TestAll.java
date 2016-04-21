package debug;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestAll {

	@Test
	public void test() {
		SupervisorTest.myTest();
		LoggerTest.myTest();
	}

}
