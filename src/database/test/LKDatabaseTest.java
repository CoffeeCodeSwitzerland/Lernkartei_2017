package database.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import database.DoorEntity;
import database.LKDatabase;
import database.sql.Entity;
import database.sql.SQLHandler;

/**
 * @author WISS
 *
 */
public class LKDatabaseTest {

	public static void myTest() {
		debug.Debugger.out("Test LKDatabase...");
		String cmd, gets;
		Entity e;
		int res;
		
		debug.Debugger.out("Test DB cleaning...");
		e = LKDatabase.myConfig;
		res = e.getMyDBDriver().executeCommand("DELETE FROM "+e.getMyTableName());
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);
		assertEquals("PK_"+e.getMyTableName(),e.getMyAttributes().getPrimaryKey().getName());
		e.getMyDBDriver().closeDB();

		e = LKDatabase.myCards;
		res = e.getMyDBDriver().executeCommand("DELETE FROM "+e.getMyTableName());
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);
		assertEquals("PK_"+e.getMyTableName(),e.getMyAttributes().getPrimaryKey().getName());
		e.getMyDBDriver().closeDB();

		e = LKDatabase.myStacks;
		res = e.getMyDBDriver().executeCommand("DELETE FROM "+e.getMyTableName());
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);
		assertEquals("PK_"+e.getMyTableName(),e.getMyAttributes().getPrimaryKey().getName());
		e.getMyDBDriver().closeDB();

		e = LKDatabase.myDoors;
		res = e.getMyDBDriver().executeCommand("DELETE FROM "+e.getMyTableName());
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);
		assertEquals("PK_"+e.getMyTableName(),e.getMyAttributes().getPrimaryKey().getName());
		e.getMyDBDriver().closeDB();

		e = LKDatabase.myUsers;
		res = e.getMyDBDriver().executeCommand("DELETE FROM "+e.getMyTableName());
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);
		assertEquals("PK_"+e.getMyTableName(),e.getMyAttributes().getPrimaryKey().getName());
		e.getMyDBDriver().closeDB();

		e = LKDatabase.mySides;
		res = e.getMyDBDriver().executeCommand("DELETE FROM "+e.getMyTableName());
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);
		assertEquals("PK_"+e.getMyTableName(),e.getMyAttributes().getPrimaryKey().getName());
		e.getMyDBDriver().closeDB();

		debug.Debugger.out("Test Config...");
		assertEquals(3,LKDatabase.myConfig.getMyAttributes().getSize());
		String seekKey = Entity.KEY_NAME;
		assertEquals("KEY_NAME",seekKey);
		String keyName = "test_key";

		// TODO check here id DB and key already exists then check for update else for insert
		e = LKDatabase.myConfig;
		gets = e.getValue(keyName);
		e.setKeyValue(keyName, "1234");
		cmd = e.getMyDBDriver().getLastSQLCommand();
		debug.Debugger.out("SQL-C: {"+cmd+"}");
		if (gets == null) {
			assertEquals("INSERT INTO config ("+Entity.KEY_NAME+", "+Entity.VALUE_NAME+") VALUES ('test_key','1234')",cmd);
		} else {
			assertEquals("UPDATE config SET "+Entity.VALUE_NAME+" = '1234' WHERE "+Entity.KEY_NAME+" = '"+keyName+"' ",cmd);
		}
		// TODO do not accept two keys of same name (UNIQUE...)
		res = e.getMyDBDriver().executeCommand(cmd);
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);

		gets = e.getValue("TestKey");
		debug.Debugger.out("SQL: {"+gets+"}");
		assertEquals(null,gets);
		gets = e.getValue(keyName);
		debug.Debugger.out("SQL: {"+gets+"}");
		assertEquals("1234",gets);
		e.getMyDBDriver().closeDB();
		
		debug.Debugger.out("Test Doors...");
		DoorEntity de = LKDatabase.myDoors;
		de.getMyAttributes().getAttributeNamedAs("name").setValue("hallo!!!");
		cmd = SQLHandler.insertIntoTableCommand(de.getMyTableName(),de.getMyAttributes());
		debug.Debugger.out("SQL-D: {"+cmd+"}");
		assertEquals("INSERT INTO Door ("+Entity.KEY_NAME+", "+Entity.VALUE_NAME+", Name) VALUES ('','','hallo!!!')",cmd);
		res = de.getMyDBDriver().executeCommand(cmd);
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);
		ArrayList<String> aList = de.getDoors();
		cmd = de.getMyDBDriver().getLastSQLCommand();
		debug.Debugger.out("DATA: {"+cmd+"}");
		assertEquals("SELECT name FROM Door",cmd);
		assertEquals(false,de.getMyDBDriver().isThereAResult()); // no more data is ok after get
		aList = de.getDoors(); // again
		debug.Debugger.out("SQL: {"+aList.get(0)+"}");
		assertEquals("hallo!!!",aList.get(0));

		de.getMyAttributes().getAttributeNamedAs("name").setValue("hallo2");
		cmd = SQLHandler.insertIntoTableCommand(de.getMyTableName(),de.getMyAttributes()); 
		debug.Debugger.out("SQL: {"+cmd+"}");
		assertEquals("INSERT INTO Door ("+Entity.KEY_NAME+", "+Entity.VALUE_NAME+", Name) VALUES ('','','hallo2')",cmd);
		res = de.getMyDBDriver().executeCommand(cmd);
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);

		int FK_ID = de.getEntityID("name","hallo2");
		debug.Debugger.out("SQL: ("+FK_ID+")");
		//assertEquals(2,FK_ID); wechselt immer, solange kein DROP TABLE, evtl. mit MIN(ID) herausfindbar
		de.getMyDBDriver().closeDB();
	
//		debug.Debugger.out("Test Stacks...");
//		e = LKDatabase.myStacks;
//		cmd = SQLHandler.selectCommand("STACK","PK_STACK","PK_STACK","franz"); 
//		debug.Debugger.out("SQL: {"+cmd+"}");
//		assertEquals("SELECT PK_STACK FROM STACK WHERE PK_STACK = 'franz' ",cmd);
//		res = e.executeCommand(cmd);
//		debug.Debugger.out("RESULT: {"+res+"}");
//		assertEquals(-1,res);
//		boolean qres = e.executeQuery(cmd);
//		debug.Debugger.out("RESULT: {"+qres+"}");
//		assertEquals(true,qres);
//		e.closeDB();
		
		debug.Debugger.out("Test Users...");
		e = LKDatabase.myUsers;
		e.getMyAttributes().getAttributeNamedAs("Username").setValue("local");
		cmd = SQLHandler.insertIntoTableCommand(e.getMyTableName(),e.getMyAttributes()); 
		debug.Debugger.out("SQL: {"+cmd+"}");
		assertEquals("INSERT INTO User ("+Entity.KEY_NAME+", "+Entity.VALUE_NAME+", ActualScore, Username, Email, Password, Salz, HighScore, UserType) VALUES ('','',0,'local','','','',0,0)",cmd);
		res = e.getMyDBDriver().executeCommand(cmd);
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);
		e.getMyDBDriver().closeDB();

		debug.Debugger.out("Test Stacks...");
		e = LKDatabase.myStacks;
		//e.closeDB();
		int door_id = LKDatabase.myDoors.getEntityID("name", "hallo2");
		int user_id = LKDatabase.myUsers.getEntityID("Username", "local");
		LKDatabase.myDoors.getMyDBDriver().closeDB();
		LKDatabase.myUsers.getMyDBDriver().closeDB();
		assertEquals(true,door_id>0);
		assertEquals(true,user_id>0);
		e.getMyAttributes().getAttributeNamedAs("PK_DOOR").setValue(door_id);
		e.getMyAttributes().getAttributeNamedAs("PK_USER").setValue(user_id);
		e.getMyAttributes().getAttributeNamedAs("name").setValue("hh1");
		cmd = SQLHandler.insertIntoTableCommand(e.getMyTableName(),e.getMyAttributes()); 
		debug.Debugger.out("SQL: {"+cmd+"}");
		assertEquals("INSERT INTO Stack ("+Entity.KEY_NAME+", "+Entity.VALUE_NAME+", Name, Description, PK_DOOR, PK_USER) VALUES ('','','hh1','',"+door_id+","+user_id+")",cmd);
		res = e.getMyDBDriver().executeCommand(cmd);
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);
		e.getMyDBDriver().closeDB();

		debug.Debugger.out("Test Cards...");
		e = LKDatabase.myCards;
		e.getMyAttributes().getAttributeNamedAs("Frontside").setValue("hallo!!!");
		cmd = SQLHandler.insertIntoTableCommand(e.getMyTableName(),e.getMyAttributes()); 
		debug.Debugger.out("SQL: {"+cmd+"}");
		assertEquals("INSERT INTO Card ("+Entity.KEY_NAME+", "+Entity.VALUE_NAME+", PK_STACK, Frontside, Backside, Priority, Color, Link, Description, Date) VALUES ('','',0,'hallo!!!','',0,'','','','')",cmd);
		res = e.getMyDBDriver().executeCommand(cmd);
		debug.Debugger.out("RESULT: {"+res+"}");
		assertEquals(true,res>=0);
		e.getMyDBDriver().closeDB();
	}

	@Test
	public void test() {
		debug.Debugger.out("Start LKDatabase Test...");
		myTest();
	}

}
