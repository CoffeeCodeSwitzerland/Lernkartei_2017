package database.test;

import org.junit.Test;

import debug.Debugger;

public class DatabaseTest {

	public static void myTest() {
//		assertEquals(true, Debugger.isDebugActive()); // check default state
//		Debugger.setDebugActive(false);
//		assertEquals(false, Debugger.isDebugActive()); // check inactive state
	}

	@Test
	public void test() {
		String out = "Start Database Test...";
		Debugger.out(out);
		myTest();
	}
}
