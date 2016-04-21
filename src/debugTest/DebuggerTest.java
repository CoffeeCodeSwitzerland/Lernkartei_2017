package debugTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import debug.Debugger;

public class DebuggerTest {

	public static void myTest() {
		assertEquals(true, Debugger.isDebugActive()); // check default state
		Debugger.setDebugActive(false);
		assertEquals(false, Debugger.isDebugActive()); // check inactive state
	}

	@Test
	public void test() {
		String out = "Dies ist ein Test...";
		Debugger.out(out);
		for (int i = 0; i < out.length(); i++) {
			Debugger.out();
		}
		myTest();
	}

}
