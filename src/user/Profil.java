package user;

import java.util.ArrayList;

import controls.Globals;
import database.*;
import debug.Debugger;

public class Profil
{
	
	ArrayList<String> cards = new ArrayList<String>();
	
	//Alle Daten Holen
	private ArrayList<String> OriginalData = new ArrayList<String>();
	
	public Profil() {
		getOriginalData();
	}
	
	private void getOriginalData()
	{
		Score s = new Score();
		//s.generateTestdata("Insert into Score (Kartei, Score) values ('eins' , 10),('zwei' , 20),('drei' , 30)");
		//s.dropTestData("DROP TABLE Score");
		OriginalData = s.getScores();
	}
	
	public ArrayList<String> getKarteien() 
	{
		ArrayList<String> tempList = new ArrayList<String>();
		getOriginalData();
		
		String temporary = "";
		for (int i = 0; i < OriginalData.size(); i++)
		{
			String[] temp;
			temp = OriginalData.get(i).split(Globals.SEPARATOR);
			Debugger.out(i + " Kartei: " + temp[0] + " " + temp[1]);
			temporary = temp[0];
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
		
		for (int i = 0; i < OriginalData.size(); i++)
		{
			String[] temp;
			temp = OriginalData.get(i).split(Globals.SEPARATOR);
			Debugger.out(i + " Punkte: " + temp[0] + " " + temp[1]); 
			temporary = temp[1];
			tempList.add(temporary);
			temporary = "";
		}
		return tempList;
	}
}
