package globals.test;

import static org.junit.Assert.*;

import org.junit.Test;

import debug.Debugger;
import globals.Environment;

public class EnvironmentTest {

	public static void myTest() {
		String test =Environment.getFileSep();
		assertEquals("\\", test);
		assertEquals("/", Environment.getFileSep());
	}

	@Test
	public void test() {
		String out = "This is: " + this.getClass();
		Debugger.out(out);
		for (int i = 0; i < out.length(); i++)
			Debugger.out();
		Debugger.out(Environment.getEndOfLine());
		Debugger.out("Working paths:");
		Debugger.out("- class  path:" + Environment.getClassPath());
		Debugger.out("- actual path:" + Environment.getActualPath());
		Debugger.out("- home   path:" + Environment.getHomePath());
		myTest();
	}

}
