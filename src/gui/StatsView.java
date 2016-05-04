package gui;

import java.util.ArrayList;

import application.Constants;
import mvc.Controller;
import mvc.FXView;

/**
 * Diese Klasse soll die gleiche Funktionalität wie StatisticsView haben und diese dann auch ersetzen
 * Sie soll beliebig viele Säulen generieren
 * 
 * @Joel Du hast Skizzen dazu gemacht um schneller zu entwickeln
 * 
 * @author Joel Häberli
 *
 */

public class StatsView extends FXView
{

	public StatsView(String setName, Controller controller)
	{
		super(setName, controller);
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
			Points[i] = Double.parseDouble(temp[1]);
		}
	}

	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshView()
	{
		// TODO Auto-generated method stub
		
	}
	
}
