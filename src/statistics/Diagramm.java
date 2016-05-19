package statistics;

import java.util.ArrayList;

import database.Categories;
import database.Database;

public class Diagramm
{	
	ArrayList<String> Stacks = new ArrayList<String>();
	ArrayList<Double> Punkte = new ArrayList<Double>(); 
	
	Categories C = new Categories();
	public ArrayList<String> getKarteien() {
		Stacks = C.getStacknames();
		return Stacks;
	}
	
	public ArrayList<Double> getPunkte()
	{
		System.out.println("Profil 1");
		for (int i = 1; i < Stacks.size(); i++)
		{
			int[] temp = Database.getScore(Stacks.get(i).toString());
			double result = temp[0] / temp[1] * 100;
			Punkte.add(result);
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
