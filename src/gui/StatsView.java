package gui;

import java.util.ArrayList;
import java.util.function.ToDoubleFunction;

import application.Constants;
import application.MainController;
import javafx.stage.Stage;

/**
 * Diese Klasse soll die gleiche Funktionalität wie StatisticsView haben und diese dann auch ersetzen
 * Sie soll beliebig viele Säulen generieren
 * 
 * @Joel Du hast Skizzen dazu gemacht um schneller zu entwickeln
 * 
 * @author Joel Häberli
 *
 */

public class StatsView extends View
{

	public StatsView(String setName, Stage primary, MainController controller)
	{
		super(setName, primary, controller);
		//Alle Daten Holen
		ArrayList<String> OriginalData = new ArrayList<String>();
		//Alle Daten in zwei Arrays einteilen: Kartei[] und Points[]
		String[] Kartei = new String[OriginalData.size()];
		Double[] Points = new Double[OriginalData.size()];
		for (int i = 0; i < OriginalData.size(); i++)
		{
			String[] temp = new String[2];
			temp = OriginalData.get(i).split(Constants.SEPARATOR);
			Kartei[i] = temp[0];
			Points[i] = temp[1];
		}
	}

	@Override
	public void refreshView()
	{
		return;
	}
	
}
