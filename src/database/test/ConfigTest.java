package database.test;

import org.junit.Test;

import debug.Debugger;

public class ConfigTest {

	public static void myTest() {
//		assertEquals(true, Debugger.isDebugActive()); // check default state
//		Debugger.setDebugActive(false);
//		assertEquals(false, Debugger.isDebugActive()); // check inactive state
	}

	@Test
	public void test() {
		String out = "Start Config Test...";
		Debugger.out(out);
		myTest();
	}
}
