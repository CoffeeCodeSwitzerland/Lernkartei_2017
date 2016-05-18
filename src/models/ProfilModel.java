package models;
//z
import java.util.ArrayList;

import controls.Globals;
import database.Score;
import database.UserCards;
import javafx.collections.ObservableList;
import mvc.FXModel;
import user.Profil;
import user.User;

public class ProfilModel extends FXModel
{

	public ProfilModel(String myName)
	{
		super(myName);
	}

	//Wenn man Username ändern will bitte als paramS ein String in der Form newName:::oldName übergeben
	private static String[] Data = new String[2];
	@Override
	public int doAction(String functionName, String paramS, double paramD)
	{
		if (functionName.equals("change"))
		{
			//Ändert den Usernamen, wenn der neue nicht bereits vorhanden ist
			getArray(paramS);
			boolean success = User.setUsername(Data[0], Data[1]);
			return success ? 1 : -1;
		} else if (functionName.equals("getName"))
		{
			//Gibt den Usernamen des Users zurück
			User.getUsername();
			return 1;
		} else
		{
			//Defaultreturn
			return -2;
		}
	}

	private void getArray(String paramS)
	{
		Data = paramS.split(Globals.SEPARATOR);
	}

	Profil p = new Profil();
	//Als Query muss man die unten gebrauchten ausdrücke eingeben, damit die gewünschte Funktion aufgerufen wird
	public ArrayList<String> getDataList(String query)
	{ if (query.equals("kartei"))
		{
			return UserCards.getCards();
		} else if (query.equals("karteien"))
		{
			return p.getKarteien();
		} else if (query.equals("punkte"))
		{
			return p.getPunkte();
		} else {
			return null;
		}	
	}
	
	public ObservableList<String> getObservableDataList(String query) {
		if (query.equals("ranking")) {
			System.out.println("ProfilModel Ranking 1");
			return p.getRanking();
		} else {
			System.out.println("ProfilModel Ranking 2");
			return super.getObservableDataList(query);
		}
	}
}
