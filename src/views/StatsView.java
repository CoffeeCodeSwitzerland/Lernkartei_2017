package views;

import java.util.ArrayList;

import debug.Debugger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import models.StatisticsModel;
import mvc.fx.FXController;
import mvc.fx.FXView;
/**
 * Diese Klasse soll die gleiche Funktionalität wie StatisticsView haben und diese dann auch ersetzen
 * Sie soll beliebig viele Säulen generieren
 * 
 * @author Joel Häberli
 *
 */

public class StatsView extends FXView
{	
	public StatsView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}
	
	HBox Diagram = new HBox(50);
	HBox Controls = new HBox(50);
	HBox Rankings = new HBox(50);
	AppButton back = new AppButton("_Zurück");
	BorderPane Pane = new BorderPane();
	//Achsen erstellen
	CategoryAxis xAchse = new CategoryAxis();
	NumberAxis yAchse = new NumberAxis();
	//BarChart erstellen
	BarChart<String, Number> bc = new BarChart<String, Number>(xAchse, yAchse);
	//ListView
	@SuppressWarnings("rawtypes")
	ListView Ranks = new ListView();
			
	ArrayList<String> Karteien = new ArrayList<String>();
	ArrayList<Double> Punkte = new ArrayList<Double>();
	
	StatisticsModel SM = new StatisticsModel("statistics");
			
	@Override
	public Parent constructContainer() {
		
		//HBox für Diagramm
		Diagram.setAlignment(Pos.CENTER);
		
		//HBox für die Buttons / Controls
		Controls.setAlignment(Pos.BOTTOM_CENTER);
		Controls.setPadding(new Insets(15));
		
		//HBox für die Rankings
		Rankings.setAlignment(Pos.CENTER_LEFT);
		Rankings.setPadding(new Insets(15));
		
		//Buttons / Controls
		back.setOnAction(e -> {getController().showMainView();delOldStats();});
		Controls.getChildren().addAll(back);
		
		Pane.setCenter(Diagram);
		Pane.setBottom(Controls);
		Pane.setLeft(Rankings);
		
		return Pane;
	}

	@Override
	public void refreshView()
	{
		Karteien = SM.getDataList("karteien");
		Punkte = SM.getDoubleList("punkte");
		
		xAchse.setLabel("Karteien");
		yAchse.setLabel("Ergebnis (%)");
		
		try {
			for (int i = 0; i < Karteien.size(); i++)
			{
				Series<String, Number> thisSerie = new Series<String, Number>();
				thisSerie.setName(Karteien.get(i));
				thisSerie.getData().add(new XYChart.Data<String, Number>(Karteien.get(i), Punkte.get(i)));
				bc.getData().add(thisSerie);
			}
			
			Diagram.getChildren().addAll(bc);
			
			
		
		/*Ranks.setItems(getController().getFXModel("profil").getObservableDataList("ranking"));
		
		System.out.println("StatsView 5");
		
		Rankings.getChildren().addAll(Ranks);
		
		System.out.println("StatsView 6");*/
			
		
		} catch (Exception e) {
			Debugger.out("StatsView Exception: " + e.fillInStackTrace());
		}
	}
	
	private void delOldStats()
	{
		Karteien.clear();
		Punkte.clear();
		//bc = null;
	}

}
