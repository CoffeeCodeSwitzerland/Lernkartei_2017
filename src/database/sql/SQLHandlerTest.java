package database.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import database.sql.Attribute.Datatype;

/**
 * @author WISS
 *
 */
public class SQLHandlerTest {
	
	public static void myTest() {
		AttributeList atts = new AttributeList();
		Attribute a0 = new Attribute("Name","aaa");
		Attribute a1 = new Attribute("Name","xxx");
		Attribute a2 = new Attribute("Age",11);
		atts.add(a0);
		atts.add(a1);
		atts.add(a2);

		debug.Debugger.out("Test ENTITY...");

		Entity e = new Entity("test.db","TESTTAB","TEST_PK");

		AttributeList eaL = e.getMyAttributes();
		assertEquals(3, eaL.size());
		String ea = eaL.getCommaSeparatedList();
		debug.Debugger.out("ATTR: {"+ea+"}");
		assertEquals("KEY_NAME TEXT,VALUE TEXT,PK_TESTTAB INTEGER PRIMARY KEY AUTOINCREMENT", ea);

		debug.Debugger.out("Test Attributes...");
		String k = e.getMyAttributes().toKeyList(true);
		debug.Debugger.out("ATTR: {"+k+"}");
		assertEquals("KEY_NAME,VALUE,PK_TESTTAB", k);

		String k0 = e.getMyAttributes().toKeyList(true);
		debug.Debugger.out("ATTR: {"+k0+"}");
		assertEquals("KEY_NAME,VALUE,PK_TESTTAB", k0);

		e.addAttributes(atts);
		AttributeList eaL2 = e.getMyAttributes();
		assertEquals(5, eaL2.size());
		
		String ea2 = eaL2.getCommaSeparatedList();
		debug.Debugger.out("ATTR: {"+ea2+"}");
		assertEquals("KEY_NAME TEXT,VALUE TEXT,PK_TESTTAB INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Age INTEGER", ea2);

		assertNotEquals(null, e.createTableIfNotExists());
		e.addAttributes(atts);
		
		String cl = atts.toClause("AND");
		debug.Debugger.out("ATTR: {"+cl+"}");
		assertEquals("Name = 'aaa' AND Age = 11 ", cl);
		
		String v = atts.toValueList(false);
		debug.Debugger.out("ATTR: {"+v+"}");
		assertEquals("'aaa',11", v);
		
		String kl = atts.toKeyList(false);
		debug.Debugger.out("ATTR: {"+kl+"}");
		assertEquals("Name,Age", kl);
		
		String aa = atts.getCommaSeparatedList();
		debug.Debugger.out("ATTR: {"+aa+"}");
		assertEquals("Name TEXT,Age INTEGER", aa);
		
		String wc = SQLHandler.toWhereClause(atts);
		debug.Debugger.out("SQL: {"+wc+"}");
		assertEquals("WHERE Name = 'aaa' AND Age = 11 ", wc);
		

		debug.Debugger.out("Test DELETE...");
		e.delKeyValue(null, null);
		debug.Debugger.out("SQL: {"+e.getLastSQLCommand()+"}");
		assertEquals("DELETE FROM TESTTAB",e.getLastSQLCommand());

		debug.Debugger.out("Test CREATE...");
		String d = SQLHandler.createTableIfNotExistsCommand(e.getMyTableName(), e.getMyAttributes());
		debug.Debugger.out("SQL: {"+d+"}");
		assertEquals("CREATE TABLE IF NOT EXISTS TESTTAB (KEY_NAME TEXT,VALUE TEXT,PK_TESTTAB INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Age INTEGER)", d);
		int r = e.createTableIfNotExists();
		String last = e.getLastSQLCommand();
		debug.Debugger.out("SQL("+r+"): {"+last+"}");
		assertEquals(0, r);
		assertEquals("CREATE TABLE IF NOT EXISTS TESTTAB (KEY_NAME TEXT,VALUE TEXT,PK_TESTTAB INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Age INTEGER)", last);
		e.addAttributes(atts);

		debug.Debugger.out("Test SELECT...");
		String cmd = SQLHandler.selectCommand(e.getMyTableName(), "Name", "aaa");
		debug.Debugger.out("SQL("+r+"): {"+cmd+"}");
		assertEquals("SELECT * FROM TESTTAB WHERE Name = 'aaa' ", cmd);
		
		debug.Debugger.out("Test PKey in List...");
		PrimaryKey p = e.getMyAttributes().getPKey();
		assertEquals("PK_TESTTAB", p.getName());
		AttributeList pkList2 = new AttributeList();
		Attribute ra2 = new Attribute (p.getName());
		pkList2.add(ra2);
		KeyAttribute a = new KeyAttribute("Name", "aaa", Datatype.TEXT);
		String sc = SQLHandler.selectCommand(e.getMyTableName(), pkList2, a);
		debug.Debugger.out("SQL: {"+sc+"}");
		assertEquals("SELECT PK_TESTTAB FROM TESTTAB WHERE Name = 'aaa' ", sc);
		
		String c = SQLHandler.selectCommand(e.getMyTableName(),p.getName(),"Name","aaa");
		debug.Debugger.out("SQL: {"+c+"}");
		assertEquals("SELECT PK_TESTTAB FROM TESTTAB WHERE Name = 'aaa' ", c);
		String cp = SQLHandler.selectCommand(e.getMyTableName(),null,"MY_PK",33);
		debug.Debugger.out("SQL: {"+cp+"}");
		assertEquals("SELECT * FROM TESTTAB WHERE MY_PK = 33 ", cp);


		debug.Debugger.out("Test INSERT...");
		String k2 = e.getMyAttributes().toKeyList(false);
		debug.Debugger.out("ATTR: {"+k2+"}");
		assertEquals("KEY_NAME,VALUE,Name,Age", k2);
		String i = SQLHandler.insertIntoTableCommand(e.getMyTableName(), atts);
		debug.Debugger.out("SQL: {"+i+"}");
		assertEquals("INSERT INTO TESTTAB (Name,Age) VALUES ('aaa',11)", i);
		
		debug.Debugger.out("Test set keys (INSERT)...");
		e.setKeyValue("hallo", "220");
		debug.Debugger.out("SQL("+r+"): {"+e.getLastSQLCommand()+"}");
		assertEquals("INSERT INTO TESTTAB (KEY_NAME,VALUE,Name,Age) VALUES ('hallo','220','aaa',11)",e.getLastSQLCommand());

		AttributeList aList = new AttributeList();
		Attribute ra = new Attribute ("PK_TESTTAB");
		aList.add(ra);
	//	String attList = aList.toKeyList(true);

		debug.Debugger.out("Test seek in Table...");
		String sn = e.seekInTable(p.getName(),"Name","qqq");
		debug.Debugger.out("SQL("+sn+"): {}");
		assertEquals(null, sn);
		String s = e.seekInTable(p.getName(),"Name","aaa");
		debug.Debugger.out("SQL("+s+"): {}");
		assertEquals(true, Integer.parseInt(s) > 0);

		// TODO test line count should be 1 PKey changes for each deleted before
		
		debug.Debugger.out("Test UPDATE...");
		p.setValue(s);
		atts.seekKeyNamed("Age").setValue("10");
		String u = SQLHandler.updateInTableCommand(e.getMyTableName(), atts, p);
		debug.Debugger.out("SQL: {"+u+"}");
		assertEquals("UPDATE TESTTAB SET Name = 'aaa' , Age = 10 WHERE PK_TESTTAB = "+s+" ", u);
		
	}

	@Test
	public void test() {
		debug.Debugger.out("Start SQLHandler Test...");
		myTest();
	}

}