package lernen;

import java.util.ArrayList;
import java.util.Date;

public class Karte
{
	private int ID;
	private int Priority;
	//kann noch keine Kartenseiten erstellen nur die schon vorhandenen Laden
	private ArrayList<Seite> Cardsites = new ArrayList<Seite>();
	private boolean wasRight = false;
	private Date lastLearntAt;
	
	public Karte(int CardID) {
		ID = CardID;
	}
	
	// TODO : Wenn DB funzt noch darin speichern
	public boolean setID(int CardID) {
		if (CardID < 0) {
			return false;
		} else {
			ID = CardID;
			return true;
		}
	}
	
	public int getID() {
		if (!(ID < 0)) {
			return ID;
		} else {
			return -1;
		}
	}
	
	public int getPriority() {
		if (Priority <= 0 && Priority > 4) {
			return 0;
		} else {
			return Priority;
		}
	}
	
	// TODO : Wenn DB funzt noch darin speichern
	public boolean setPriority(int newPriority) {
		if (newPriority <= 0 && newPriority > 4) {
			return false;
		} else {
			Priority = newPriority;
			return true;
		}
	}
	
	// TODO : Wenn DB funzt in Datenbank speichern
	// ob die Karte das letzte mal richtig oder falsch beantortet wurde
	public boolean setWasRight(boolean wereYouRight) {
		if(wereYouRight) {
			return wasRight = true;
		} else {
			return wasRight = false;
		}
	}
	
	public boolean getWasRight() {
		return wasRight;
	}
	
	// TODO : Wenn db funzt darin speichern
	// wann die Karte das letzte mal gelernt wurde
	public boolean setLastLearntAt(Date date) {
		if (date != null) {
			lastLearntAt = date;
			return true;
		} else {
			return false;
		}
	}
	
	public Date getLastLearntAt() {
		if (lastLearntAt != null) {
			return lastLearntAt;
		} else {
			return null;
		}
	}
	
	// TODO : Wenn DB funzt die Seiten davon holen
	// für jede Seite wird ein neues Onjekt der Klasse Seite instanziert die
	// Daten darin gespeichert und dann das Objekt in die Liste Cardsites
	// gespeichert.
	public boolean pullCardsitesFromDb()
	{
		return false;
	}
}
