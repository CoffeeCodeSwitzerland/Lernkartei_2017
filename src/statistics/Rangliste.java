package statistics;

import java.util.ArrayList;

import database.Categories;
import database.Database;
import debug.Debugger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Rangliste
{

	Categories C = new Categories();
	
	ArrayList<String> NamesAndPoints = new ArrayList<String>();
	ArrayList<Double> Punkte = new ArrayList<Double>();
	ArrayList<String> Stacks = new ArrayList<String>();

	public ObservableList<String> getRangliste()
	{
		getKarteien();
		getPunkte();
		sortKarteien();
		ObservableList<String> Ranking = FXCollections.observableArrayList();
		Ranking.addAll(NamesAndPoints);
		return Ranking;
	}
	
	public void getKarteien() {
		Stacks = C.getStacknames();
	}
	
	private void getPunkte() {
		for (int i = 0; i < Stacks.size(); i++)
		{
			Double[] temp = Database.getScore(Stacks.get(i).toString());
			if (temp != null) {
				Punkte.add(temp[1]);
				Debugger.out("getPunkte erreichte Punkte in diesem Set : " + temp[1]);
			} else {
				continue;
			}
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
					Debugger.out("Letzer Eintrag : " + tempSortedStacks.get(i - 1) + " " + tempPunkte.get(i - 1));
					tempPunkte.add(oldP);
					tempSortedStacks.add(oldS);
					Debugger.out("Aktueller Eintrag : " + tempSortedStacks.get(i) + " " + tempPunkte.get(i));
				}
			} else {
				tempPunkte.add(Punkte.get(i));
				tempSortedStacks.add(Stacks.get(i));
			}
			
			for (int j = 0; j < tempPunkte.size(); j++)
			{
				String Punkte = (new Double(tempPunkte.get(j)).toString());
				String Stack = tempSortedStacks.get(j);
				NamesAndPoints.add(Stack + " " + Punkte);
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
