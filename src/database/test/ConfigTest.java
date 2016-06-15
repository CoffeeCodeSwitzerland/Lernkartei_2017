package database.test;

import org.junit.Test;

import database.Config;

import static org.junit.Assert.assertEquals;

import debug.Debugger;

public class ConfigTest {

	public static void myTest()
	{
//		assertEquals(true, Debugger.isDebugActive()); // check default state
//		Debugger.setDebugActive(false);
//		assertEquals(false, Debugger.isDebugActive()); // check inactive state
		
		assertEquals(null, Config.getValue(null));
		
		assertEquals(null, Config.getValue("some"));
		Config.setValue("some", null);
		assertEquals(null, Config.getValue("some"));
		Config.setValue("some", "");
		assertEquals("", Config.getValue("some"));
		Config.setValue("some", "hello");
		assertEquals("hello", Config.getValue("some"));
		
		Config.setValue("some", "hallo");
		assertEquals("hallo", Config.getValue("some"));
	}

	@Test
	public void test()
	{
		String out = "Start Config Test...";
		Debugger.out(out);
		ConfigTest.myTest();
	}
}
