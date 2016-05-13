package user;

import java.util.ArrayList;

import controls.Globals;
import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Profil
{
	
	ArrayList<String> cards = new ArrayList<String>();
	
	//Alle Daten Holen
	private ArrayList<String> OriginalData = new ArrayList<String>();
	
	Score s = new Score();
	
	public Profil() {
		getOriginalData();
	}
	
	private void getOriginalData()
	{
		//s.generateTestdata("Insert into Score (Kartei, Score) values ('Franz' , 20),('Math' , 50),('Physik' , 40),('English' , 60)");
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
			temporary = temp[1];
			tempList.add(temporary);
			temporary = "";
		}
		return tempList;
	}
	
	public ObservableList<String> getRanking() {
		ObservableList<String> Ranking = FXCollections.observableArrayList();
		Ranking = s.getRanking();
		
		return Ranking;
	}
}
