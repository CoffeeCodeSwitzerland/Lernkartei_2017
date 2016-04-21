package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EnvironementTest {

	public static void myTest() {
		Environement e = new Environement();
		assertEquals("\\", e.getFileSep());
	}

	@Test
	public void test() {
		Environement e = new Environement();
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
