package statistics;

import java.util.ArrayList;

import database.Categories;
import database.Database;
import debug.Debugger;

public class Diagramm
{	
	
	public Diagramm() {
		Debugger.out("Klasse Diagramm zugriff");
	}
	ArrayList<String> Stacks = new ArrayList<String>();
	ArrayList<Double> Punkte = new ArrayList<Double>(); 
	
	Categories C = new Categories();
	public ArrayList<String> getKarteien() {
		Stacks = C.getStacknames();
		return Stacks;
	}
	
	public ArrayList<Double> getPunkte()
	{		
		for (int i = 0; i < Stacks.size(); i++)
		{
			Debugger.out("getPunkte 1");
			int[] temp = Database.getScore(Stacks.get(i).toString());
			Debugger.out("getPunkte 2");
			double result = temp[0] / temp[1] * 100;
			Debugger.out("getPunkte 3");
			Punkte.add(result);
			Debugger.out("Durchlauf in getPunkte" + i);
		}		
		return Punkte;
	}
	
	public Boolean delOldStats()
	{
		Stacks = null;
		Punkte = null;
		return true;
	}
	
	

}
