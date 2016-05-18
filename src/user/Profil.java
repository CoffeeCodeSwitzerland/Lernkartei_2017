package user;

import java.util.ArrayList;

import database.Categories;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Profil
{

	ArrayList<String> cards = new ArrayList<String>();

	// Alle Daten Holen
	private ArrayList<String> Stacks = new ArrayList<String>();
	private ArrayList<String> Punkte = new ArrayList<String>();

	Categories c = new Categories();
	Database d = new Database();

	public Profil()
	{
		Stacks = c.getStacknames();
	}

	public ArrayList<String> getKarteien()
	{
		return Stacks;
	}

	public ArrayList<String> getPunkte()
	{
		System.out.println("Profil 1");
		for (int i = 1; i < Stacks.size(); i++)
		{
			System.out.println("Profil 2");
			int[] temp = Database.getScore(Stacks.get(i).toString());
			System.out.println("Profil 3 " + "Reached points: " + temp[0]);
			System.out.println("Profil 3 " + "Max points: " + temp[1]);
			double result = temp[0] / temp[1] * 100;
			System.out.println("Profil 4 " + "Resultat: " + temp[0] / temp[1] * 100);
			String tempStr = new Double(result).toString();
			System.out.println("Profil 5 " + "String Resultat: " + tempStr);
			Punkte.add(tempStr);
			System.out.println("Profil 6 ");
		}
		
		System.out.println("Profil 7");
		return Punkte;
	}

	public ObservableList<String> getRanking()
	{
		ObservableList<String> Ranking = FXCollections.observableArrayList();
		Double[] RankStackpoints = new Double[Stacks.size()];
		String[] RankStackName = new String[Stacks.size()];
		
		System.out.println("Profil getRanking 1");
		
		for (int i = 1; i < Punkte.size(); i++)
		{
			Double punkte = Double.parseDouble(Punkte.get(i));
			System.out.println("Profil getRanking 1.1 " + punkte);
			int lastIndex = i - 1;
			System.out.println("Profil getRanking 2 " + lastIndex);
			if (punkte >= RankStackpoints[lastIndex])
			{
				System.out.println("Profil getRanking 3");
				RankStackpoints[i] = Double.parseDouble(Punkte.get(i));
				RankStackName[i] = Stacks.get(i);
			} else {
				System.out.println("Profil getRanking 4");
				if (punkte < RankStackpoints[lastIndex])
				{
					System.out.println("Profil getRanking 5");
					Double letzterEintrag = RankStackpoints[lastIndex];
					RankStackpoints[lastIndex] = Double.parseDouble(Punkte.get(i));
					RankStackpoints[i] = letzterEintrag;
					String lastEntry = RankStackName[lastIndex];
					RankStackName[lastIndex] = Stacks.get(i);
					RankStackName[i] = lastEntry;
				} else
				{
					System.out.println("Profil getRanking 6");
					return null;
				}
			}
		}

		System.out.println("Profil getRanking 7");
		for (int j = 0; j < RankStackName.length; j++)
		{
			System.out.println("Profil getRanking 8");
			Ranking.add(RankStackName[j]);
		}

		System.out.println("Profil getRanking 9");
		return Ranking;
	}
}
