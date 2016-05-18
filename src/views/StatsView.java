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
	ArrayList<String> Punkte = new ArrayList<String>();
			
	@Override
	public Parent constructContainer() {
		
		//HBox für das Diagramm
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
	
	//Eine Serie erstellen
	@SuppressWarnings("rawtypes")
	Series serie = new Series();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void refreshView()
	{
		
		try {
		xAchse.setLabel("Kartei");
		yAchse.setLabel("Ergebnis(%)");
		
		System.out.println("StatsView 1");
		Karteien = getController().getModel("profil").getDataList("karteien");
		System.out.println("StatsView 2");
		Punkte = getController().getModel("profil").getDataList("punkte");
		System.out.println("StatsView 3");
		
		System.out.println("StatsView Kartei 1: " + Karteien.get(0));
		System.out.println("StatsView Punkte 1: " + Punkte.get(0));
		
		for (int i = 0; i < Karteien.size(); i++)
		{
			Double tempPunkte = Double.parseDouble(Punkte.get(i));
			serie.getData().add(new XYChart.Data(Karteien.get(i), tempPunkte));
		}
		
		/*System.out.println("StatsView 4");
		
		Ranks.setItems(getController().getFXModel("profil").getObservableDataList("ranking"));
		
		System.out.println("StatsView 5");
		
		Rankings.getChildren().addAll(Ranks);
		
		System.out.println("StatsView 6");*/
		
		bc.getData().addAll(serie);
		Diagram.getChildren().addAll(bc);
		} catch (Exception e) {
			Debugger.out("StatsView Exception: " + e.getMessage());
		}
	}
	
	private void delOldStats()
	{
		Karteien = null;
		Punkte = null;
		serie = null;
	}
}
