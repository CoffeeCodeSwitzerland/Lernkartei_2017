package user;

import java.util.ArrayList;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Profil
{
	
	ArrayList<String> cards = new ArrayList<String>();
	
	//Alle Daten Holen
	private ArrayList<String> Stacks = new ArrayList<String>();
	private ArrayList<String> Punkte = new ArrayList<String>();
	
	Categories c = new Categories();
	Database d = new Database();
	
	public Profil() {
		Stacks = c.getStacknames();
	}
	
	public ArrayList<String> getKarteien() 
	{
		return Stacks;
	}
	
	@SuppressWarnings("static-access")
	public ArrayList<String> getPunkte() 
	{
		for (int i = 1; i < Stacks.size(); i++)
		{
			int temp[] = d.getScore(Stacks.get(i));
			double result = temp[1] / temp[0] * 100; 
			String tempStr = new Double(result).toString();
			Punkte.add(tempStr);
		}
		return Punkte;
	}
	
	//TODO Ranking erstellen
	public ObservableList<String> getRanking() {
		ObservableList<String> Ranking = FXCollections.observableArrayList();
		Double[] RankStackpoints = new Double[Stacks.size()];
		String[] RankStackName = new String[Stacks.size()];
		for (int i = 0; i < Punkte.size(); i++)
		{
			Double punkte = Double.parseDouble(Punkte.get(i));
			int lastIndex = i - 1;
			if (punkte >= RankStackpoints[lastIndex]) {
				RankStackpoints[i] = Double.parseDouble(Punkte.get(i));
				RankStackName[i] = Stacks.get(i);
			} else if (punkte < RankStackpoints[lastIndex]) {
				Double letzterEintrag = RankStackpoints[lastIndex];
				RankStackpoints[lastIndex] = Double.parseDouble(Punkte.get(i));
				RankStackpoints[i] = letzterEintrag;
				String lastEntry = RankStackName[lastIndex];
				RankStackName[lastIndex] = Stacks.get(i);
				RankStackName[i] = lastEntry;
			} else {
				return null;
			}
		}
		
		for (int j = 0; j < RankStackName.length; j++)
		{
			Ranking.add(RankStackName[j]);
		}
		
		return Ranking;
	}
}
