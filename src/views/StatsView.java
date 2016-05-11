package views;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;
import models.*;
import mvc.Controller;
import mvc.FXView;
/**
 * Diese Klasse soll die gleiche Funktionalität wie StatisticsView haben und diese dann auch ersetzen
 * Sie soll beliebig viele Säulen generieren
 * 
 * @author Joel Häberli
 *
 */

public class StatsView extends FXView
{	
	public StatsView(String newName, Controller newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	//Zugehöriges Model deklarieren und instanzieren
	ProfilModel Pm = new ProfilModel("heee?");

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Parent constructContainer() {
//		Karteien = Pm.getDataList("karteien");
//		Punkte = Pm.getDataList("punkte");
		
		//HBox für das Diagramm
		HBox Diagram = new HBox();
		Diagram.setAlignment(Pos.CENTER);
		
		//HBox für die Buttons / Controls
		HBox Controls = new HBox();
		Controls.setAlignment(Pos.BOTTOM_CENTER);
		
		//Buttons / Controls
		AppButton back = new AppButton("Zurück");
		
		//*********************************Diagramm Start*********************************//
		//Achsen erstellen
		CategoryAxis xAchse = new CategoryAxis();
		NumberAxis yAchse = new NumberAxis();
		
		xAchse.setLabel("Kartei");
		yAchse.setLabel("Punkte");
		
		//BarChart erstellen
		BarChart<String, Number> bc = new BarChart<String, Number>(xAchse, yAchse);
		
		ArrayList<String> Karteien = new ArrayList<String>();
		ArrayList<String> Punkte = new ArrayList<String>();
		
		Karteien = Pm.getDataList("karteien");
		System.out.println("Letzter Eintrag Karteien : " + Karteien.get(Karteien.size() - 1));
		Punkte = Pm.getDataList("punkte");
		System.out.println("Letzter Eintrag Punkte: " + Punkte.get(Punkte.size() - 1));
		
		//Eine Serie erstellen
		Series serie = new Series();
		for (int i = 0; i < Karteien.size(); i++)
		{
			Double tempPunkte = Double.parseDouble(Punkte.get(i));
			serie.getData().add(new XYChart.Data(Karteien.get(i), tempPunkte));
		}
	
		bc.getData().addAll(serie);
		
		//*********************************Diagramm Ende**********************************//
		
		//Szene setzen und Buttons Event
		back.setOnAction(e -> getController().getMainViewName());
		return bc;
	}

	@Override
	public void refreshView()
	{
	}
}
