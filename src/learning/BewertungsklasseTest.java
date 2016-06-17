package learning;

import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.CardEntity;

public class BewertungsklasseTest
{

	private static final String dbURL = "jdbc:sqlite:" + globals.Environment.getDatabasePath() + globals.Globals.db_name + ".db";
	private static final Statement stmt = null;

	
	//Bevor der effektive Test startet, füge ich hier Testdaten in die Datenbank ein, damit ich die Funktionen einfacher testen kann
	@Before
	public void setUp() throws Exception
	{
		//Datenbank mit Beispieldaten füllen
		CardEntity.setConnection(dbURL);
		try {
					
			String testKategorie = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";
			
			debug.Debugger.out(testKategorie);
			stmt.executeUpdate(testKategorie);
			
			String testDataKategorie = "INSERT INTO Kategorie (Kategorie) VALUES ('TestKategorie')";
			
			debug.Debugger.out(testDataKategorie);
			stmt.executeUpdate(testDataKategorie);
			
			String testStock = "CREATE TABLE IF NOT EXISTS Stock " +
					"(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Frontside       TEXT    NOT NULL, " +
					" Backside      TEXT    NOT NULL, " +
					" Set_ID    		INTEGER NOT NULL, " +
					" Priority	    INTEGER DEFAULT 1," +
					" Description    TEXT    		, " +
					" Color			TEXT    		 )";

			debug.Debugger.out(testStock);
			stmt.executeUpdate(testStock);
			
			String testInsert = "INSERT INTO Stock (Frontside, Backside, Set_ID) VALUES ('TestFront', 'TestBack', (SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = 'TestKategorie')";
			
			debug.Debugger.out(testInsert);
			stmt.executeUpdate(testInsert);
			
			//TODO : PK_Stk in Integer (whichSet) konvertieren und danach ein Globales ResultSet mit diesem füllen.
			
			ResultSet PK_Stk = stmt.executeQuery("SELECT PK_Stk FROM Stock WHERE Set_ID =  (SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = 'TestKategorie')");
			debug.Debugger.out(PK_Stk.toString());
			
			String pkstk = PK_Stk.getString(1);
			debug.Debugger.out(pkstk);
			

		} catch (Exception e) 
		{
			debug.Debugger.out("@Befor isn't working");
		}
	}

	//Nach dem Test Lösche
	@After
	public void tearDown() throws Exception
	{
		//TODO : Datenbank Beispieldaten löschen
	}

	@Test
	public void test()
	{
		testGetShuffledCards();
		testCardCorrect();
		testCardFalse();
		testDatumZuweisen();
	}
	
	private void testGetShuffledCards() 
	{
		//TODO : Test schreiben
		
	}
	
	private void testCardCorrect() 
	{
		//TODO : Test schreiben
	}
	
	private void testCardFalse()
	{
		//TODO : Test schreiben
	}

	private void testDatumZuweisen() 
	{
		//TODO : Test schreiben
	}
}
