package statistics;

import java.util.ArrayList;

import database.Stack;
import debug.Debugger;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Rangliste
{
	private static ArrayList<String> NamesAndPoints = new ArrayList<String>();
	private static ArrayList<Double> Punkte = new ArrayList<Double>();
	private static ArrayList<String> Stacks = new ArrayList<String>();

	private static boolean isFilled = false;
	
	public static boolean checkDatabase() {
		
		ArrayList<String> testStacks = new ArrayList<String>();
		ArrayList<Double> testPoints = new ArrayList<Double>();
		
		if ((testStacks.isEmpty() || testPoints.isEmpty()) || (testStacks.isEmpty() && testPoints.isEmpty())) {
			isFilled = false;
		} else {
			isFilled = true;
		}
		return isFilled;
	}

	static ObservableList<String> Ranking = FXCollections.observableArrayList();
	public static ObservableList<String> getRangliste()
	{
		
		resetData();
		
		getKarteien();
		getPunkte();
		sortKarteien();
		Ranking.addAll(NamesAndPoints);
		return Ranking;
	}
	
	public static void getKarteien() {
		if (Stack.getStacknames() == null || Stack.getStacknames().get(0).equals("")) 
		{
			Debugger.out("Rangliste: getKarteien if");
			Stacks = null;			
		} 
		else 
		{
			Debugger.out("Rangliste: getKarteien Else");
			Stacks = Stack.getStacknames();
		}
	}
	
	private static void getPunkte() {
		if (Stacks == null || Stacks.get(0).equals(""))
		{
			Punkte.add(-1.0);
		} 
		else
		{
			for (int i = 0; i < Stacks.size(); i++)
			{
				Double[] temp = Database.getScore(Stacks.get(i).toString());
				if (temp != null) {
					Punkte.add(temp[1]);
				} else {
					continue;
				}
			}
		}
	}
	
	private static void sortKarteien() {
		
		ArrayList<Double> tempPunkte = new ArrayList<Double>();
		ArrayList<String> tempSortedStacks = new ArrayList<String>();
		
		try  {
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
			fillNamesAndPoints(tempSortedStacks, tempPunkte);
			
			for (int i = 0; i < NamesAndPoints.size(); i++)
			{
				for (int j = i+1; j < NamesAndPoints.size(); j++)
				{
					if (NamesAndPoints.get(i).equals(NamesAndPoints.get(j))) {
						NamesAndPoints.remove(j);
					} else {
						continue;
					}
				}
			}
			tempPunkte.clear();
			tempSortedStacks.clear();
			
			
		} catch (Exception e) {
			Debugger.out("sortKarteien : " + e.getMessage());
			Debugger.out("sortKarteien : " + e.fillInStackTrace());
			
		}
	}
	
	//Diese Funktion f�llt die sortierten Listen in eine Liste ein, in Welcher Punktzahl und Name in einem String stehen.
	private static void fillNamesAndPoints(ArrayList<String> Stack, ArrayList<Double> Points)
	{
		for (int i = 0; i < Stack.size(); i++)
		{
			String p = (Points.get(i)).toString();
			String s = (Stack.get(i));
			String together = s + "     " + p;
			NamesAndPoints.add(together);
		}
	}

	public static Boolean resetData() {
		Punkte.clear();
		Stacks.clear();
		NamesAndPoints.clear();
		return true;
	}
}
