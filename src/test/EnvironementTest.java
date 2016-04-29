package debugTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import debug.Debugger;
import debug.Environment;

public class EnvironementTest {

	public static void myTest() {
		Environment e = new Environment();
		assertEquals("\\", e.getFileSep());
	}

	@Test
	public void test() {
		Environment e = new Environment();
		String out = "This is: " + this.getClass();
		Debugger.out(out);
		for (int i = 0; i < out.length(); i++)
			Debugger.out();
		Debugger.out(e.getEndOfLine());
		Debugger.out("Working paths:");
		Debugger.out("- class  path:" + e.getClassPath());
		Debugger.out("- actual path:" + e.getActualPath());
		Debugger.out("- home   path:" + e.getHomePath());
		myTest();
	}

}
