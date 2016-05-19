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
			Double[] temp = Database.getScore(Stacks.get(i).toString());
			Double result = (100 / temp[0]) * temp[1];
			Debugger.out("Resultat : " + result);
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
