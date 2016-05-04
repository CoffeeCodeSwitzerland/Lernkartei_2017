package gui;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;
import mvc.*;
import models.*;
/**
 * Diese Klasse soll die gleiche Funktionalität wie StatisticsView haben und diese dann auch ersetzen
 * Sie soll beliebig viele Säulen generieren
 * 
 * @author Joel Häberli
 *
 */

public class StatsView extends FXView
{	
	//Zugehöriges Model deklarieren und instanzieren
	ProfilModel Pm = new ProfilModel("heee?");
	
	//Arrays für Daten welche ich von ProfilModel erhalte
//	private ArrayList<String> Karteien;
//	private ArrayList<String> Punkte;
	
	
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
	public StatsView(String setName, Controller controller)
	{
		super(setName, controller);
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
		
		//Eine Serie erstellen
//		Series serie = new Series();
//		for (int i = 0; i < Karteien.size(); i++)
//		{
//			serie.getData().add(new XYChart.Data(Karteien.get(i), Punkte.get(i)));
//		}
		
		//Neue Szene
		Scene scene = new Scene(bc);
//		bc.getData().addAll(serie);
		
		//Szene setzen und Buttons Event
		this.setupScene(scene);
		back.setOnAction(e -> getController().getMainView());
	}

	@Override
	public void refreshView()
	{
		
	}
	
}
