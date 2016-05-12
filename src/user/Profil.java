package user;

import java.util.ArrayList;

import controls.Globals;
import database.*;

public class Profil
{
	
	ArrayList<String> cards = new ArrayList<String>();
	
	//Alle Daten Holen
	@SuppressWarnings ("unused")
	private ArrayList<String> OriginalData = new ArrayList<String>();
	
	//Alle Daten in zwei Arrays einteilen: Kartei[] und Points[]
	private String[] Kartei;
	private String[] Points;
	
	public Profil() {
		generateTestdata();
		
		Kartei = new String[cards.size()];
		Points = new String[cards.size()];
	}
	
	//Daten zu Testzwecken
	private void generateTestdata() {
	
	String eins = "eins:::10";
	String zwei = "zwei:::20";
	String drei = "drei:::30";
		
	cards.add(eins);
	cards.add(zwei);
	cards.add(drei);
	
	}
	
	private void getOriginalData()
	{
		Score s = new Score();
		OriginalData = s.getScores();
	}
	
	public ArrayList<String> getKarteien() 
	{
		ArrayList<String> tempList = new ArrayList<String>();
		getOriginalData();
		
		String temporary = "";
		for (int i = 0; i < /*OriginalData*/cards.size(); i++)
		{
			String[] temp;
			temp = cards.get(i).split(Globals.SEPARATOR);
//			temp = OriginalData.get(i).split(Globals.SEPARATOR);
			System.out.println("Kartei: " + temp[0] + " " + temp[1]);
			Kartei[i] = temp[0];
			System.out.println(temp);
			temporary = Kartei[i];
			tempList.add(temporary);
			temporary = "";
		}
		return tempList;
	}
	
	public ArrayList<String> getPunkte() 
	{
		ArrayList<String> tempList = new ArrayList<String>();
		getOriginalData();
		
		String temporary = "";
		
		for (int i = 0; i < /*OriginalData*/cards.size(); i++)
		{
			String[] temp;
			temp = cards.get(i).split(Globals.SEPARATOR);
//			temp = OriginalData.get(i).split(Globals.SEPARATOR);
			System.out.println("Punkte: " + temp[0] + " " + temp[1]);
			Points[i] = temp[1];
			temporary = Points[i];
			tempList.add(temporary);
			temporary = "";
		}
		return tempList;
	}
}
