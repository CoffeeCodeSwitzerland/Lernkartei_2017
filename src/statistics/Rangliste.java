package statistics;

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

		for (int i = 0; i < Punkte.size(); i++)
		{
			if (i != 0) {
				if (Punkte.get(i) > Punkte.get(i - 1)) {
					tempPunkte.add(Punkte.get(i));
					tempSortedStacks.add(Stacks.get(i));
				} else {
					Double oldP = Punkte.get(i - 1);
					String oldS = Stacks.get(i - 1);
					tempPunkte.add(i - 1, Punkte.get(i));
					tempSortedStacks.add(i - 1, Stacks.get(i));
					tempPunkte.add(oldP);
					tempSortedStacks.add(oldS);
				}
			} else {
				tempPunkte.add(Punkte.get(i));
				tempSortedStacks.add(Stacks.get(i));
			}
		}
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
