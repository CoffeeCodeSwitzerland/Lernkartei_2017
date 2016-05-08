package user;

import java.util.ArrayList;

import controls.Globals;
import database.*;

public class Profil
{
	
	//Alle Daten Holen
	private static ArrayList<String> OriginalData = new ArrayList<String>();
	
	//Alle Daten in zwei Arrays einteilen: Kartei[] und Points[]
	private static String[] Kartei = new String[OriginalData.size()];
	private static String[] Points = new String[OriginalData.size()];
	
	private static void getOriginalData()
	{
		OriginalData = Score.getScores();
	}
	
	public static ArrayList<String> getKarteien() 
	{
		ArrayList<String> tempList = new ArrayList<String>();
		getOriginalData();
		
		String temporary = null;
		for (int i = 0; i < OriginalData.size(); i++)
		{
			String[] temp;
			temp = OriginalData.get(i).split(Globals.SEPARATOR);
			Kartei[i] = temp[0];
			System.out.println(temp);
			System.out.println();
			String oldString = temporary;
			temporary = oldString + Kartei[i] + Globals.SEPARATOR;
		}
		return tempList;
	}
	
	public static ArrayList<String> getPunkte() 
	{
		ArrayList<String> tempList = new ArrayList<String>();
		getOriginalData();
		
		String temporary = null;
		
		for (int i = 0; i < OriginalData.size(); i++)
		{
			String[] temp;
			temp = OriginalData.get(i).split(Globals.SEPARATOR);
			Points[i] = temp[1];
			String oldString = temporary;
			temporary = oldString + Points[i] + Globals.SEPARATOR;
		}
		return tempList;
	}
}
