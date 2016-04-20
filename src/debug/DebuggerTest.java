package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DebuggerTest {

	@Test
	public void test() {

		assertEquals(true, Debugger.isDebugActive());
		Debugger.setDebugActive(false);
		assertEquals(false, Debugger.isDebugActive());
		assertEquals(false, Debugger.isDebugActive());

		//fail("Not yet implemented");
	}

}
