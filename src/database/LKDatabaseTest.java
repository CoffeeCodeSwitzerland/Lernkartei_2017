package database;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import database.sql.SQLHandler;

/**
 * @author WISS
 *
 */
public class LKDatabaseTest {

	public static void myTest() {
		debug.Debugger.out("Test LKDatabase...");
		String cmd, gets;

		debug.Debugger.out("Test Config...");
		LKDatabase.myConfig.setKeyValue("TestKey", "1234");
		cmd = LKDatabase.myConfig.getLastSQLCommand();
		debug.Debugger.out("SQL: {"+cmd+"}");
		assertEquals("INSERT INTO config (KEY_NAME,VALUE) VALUES ('TestKey','1234')",cmd);
		gets = LKDatabase.myConfig.getValue("TestKey");
		debug.Debugger.out("SQL: {"+gets+"}");
		assertEquals("1234",gets);
		
		LKDatabase.myDoors.getMyAttributes().seekKeyNamed("name").setValue("hallo!!!");
		cmd = SQLHandler.insertIntoTableCommand(LKDatabase.myDoors.getMyTableName(),LKDatabase.myDoors.getMyAttributes()); 
		//cmd = LKDatabase.myDoors.getLastSQLCommand();
		debug.Debugger.out("SQL: {"+cmd+"}");
		assertEquals("INSERT INTO Door (KEY_NAME,VALUE,Name) VALUES ('','','hallo!!!')",cmd);
	
	}

	@Test
	public void test() {
		debug.Debugger.out("Start LKDatabase Test...");
		myTest();
	}

}
