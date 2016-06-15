package models;

import java.util.ArrayList;

import database.UserCards;
import debug.Debugger;
import debug.Supervisor;
import javafx.collections.ObservableList;
import mvc.fx.FXModel;
import user.Profil;
import user.User;

/**
 * 
 * @author joel häberli
 *
 */
public class ProfilModel extends FXModel
{
	//Wenn man Username ändern will bitte als paramS ein String in der Form newName:::oldName übergeben
	@Override
	public int doAction(String functionName, String paramS, double paramD)
	{
		if (functionName.equals("change"))
		{
			//Ändert den Usernamen, wenn der neue nicht bereits vorhanden ist
			
			Supervisor.errorAndDebug(this, "ProfilModel: Method deprecated! Please use the new doAction!");
			
			return -1;
		}
		else if (functionName.equals("getName"))
		{
			//Gibt den Usernamen des Users zurück
			Supervisor.errorAndDebug(this, "ProfilModel: Method deprecated! Please use the new doAction!");
			
			return -1;
		}
		else
		{
			//Defaultreturn
			return 0;
		}
	}
	
	@Override
	public int doAction(Command command, String... param)
	{
		switch (command)
		{
			case UPDATE:
				if (param.length != 2) { return -2; }
				
				boolean success = User.setUsername(param[0], param[1]);
				return success ? 1 : -1;
				
			case GET:
				if (param.length != 0) { Debugger.out("INFO (ProfilModel) : No params are needed"); }
				
				User.getUsername();
				return 1;
				
			default:
				return super.doAction(command, param);
		}
	};

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
			debug.Debugger.out("ProfilModel Ranking 1");
			return p.getRanking();
		} else {
			debug.Debugger.out("ProfilModel Ranking 2");
			return super.getObservableDataList(query);
		}
	}
}
