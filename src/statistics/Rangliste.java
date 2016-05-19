package statistics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import database.Categories;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Rangliste
{
	
	public Rangliste() {
		getKarteien();
		getPunkte();
		sortKarteien();
	}

	Categories C = new Categories();
	
	ArrayList<String> NamesAndPoints = new ArrayList<String>();
	ArrayList<Double> Punkte = new ArrayList<Double>();
	ArrayList<String> Stacks = new ArrayList<String>();

	public ObservableList<String> getRangliste()
	{
		ObservableList<String> Ranking = FXCollections.observableArrayList();
		Ranking.addAll(NamesAndPoints);
		return null;
	}
	
	public void getKarteien() {
		Stacks = C.getStacknames();
	}
	
	private void getPunkte() {
		for (int i = 0; i < Stacks.size(); i++)
		{
			Double[] temp = Database.getScore(Stacks.get(i).toString());
			Punkte.add(temp[1]);
		}
	}
	
	private void sortKarteien() {
		
		ArrayList<Double> tempPunkte = new ArrayList<Double>();
		ArrayList<String> tempSortedStacks = new ArrayList<String>();

		Boolean isFirstSet = false;
		/*for (int i = 0; i < array.length; i++)
		{
			if (isFirstSet) {
				
			} else {
				tempPunkte
				isFirstSet = true;
			}
		}*/
	}

	/*private static String url = "jdbc:sqlite:" + controls.Environment.getDatabasePath() + controls.Globals.db_name + ".db";
	private static String driver = "org.sqlite.JDBC";

	private void getStacks()
	{

		Connection c = null;
		Statement stmt = null;

		try
		{
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			String searchStacks = "SELECT ";
			ResultSet rs = stmt.executeQuery(searchStacks);
			
		} catch (Exception e)
		{

		}

	}*/
}
