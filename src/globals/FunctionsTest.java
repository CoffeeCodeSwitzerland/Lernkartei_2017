package globals;

import static org.junit.Assert.*;

import org.junit.Test;

import debug.Debugger;

public class FunctionsTest
{

	@Test
	public void test ()
	{
		assertEquals(Functions.simpleBbCode2HTML("[sup][/sup]", "sup"), "<sup></sup>");
		assertEquals(Functions.simpleBbCode2HTML("[sup]Hallo [s]Welt[/s][/sup]", "sup", "s"), "<sup>Hallo <s>Welt</s></sup>");
		

		assertEquals(Functions.realBbCode2HTML("[b][/b]", "b", "strong"), "<strong></strong>");
		
		boolean debug = Debugger.isDebugActive();
		Debugger.setDebugActive(false);
		assertEquals(Functions.realBbCode2HTML("[b][/b]", "b"), null);
		Debugger.setDebugActive(debug);

		assertEquals(Functions.realBbCode2HTML(Functions.simpleBbCode2HTML("Hello [b]bold[/b] [s]line[/s] [u]", "s", "u"), "b", "strong"), "Hello <strong>bold</strong> <s>line</s> [u]");
	}

}
