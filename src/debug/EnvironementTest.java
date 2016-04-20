package debug;

import static org.junit.Assert.*;

import org.junit.Test;

public class EnvironementTest {

	@Test
	public void test() {
		Environement e = new Environement();
		assertEquals("/",e.getFileSep());
		//fail("Not yet implemented");
	}

}
