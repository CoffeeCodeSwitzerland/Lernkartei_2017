package views;

import java.util.ArrayList;

import debug.Debugger;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import mvc.Controller;
import mvc.FXView;
import mvc.Model;
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
	
	HBox Diagram = new HBox();
	HBox Controls = new HBox();
	AppButton back = new AppButton("Zurück");
	BorderPane Pane = new BorderPane();
	//Achsen erstellen
	CategoryAxis xAchse = new CategoryAxis();
	NumberAxis yAchse = new NumberAxis();
	//BarChart erstellen
	BarChart<String, Number> bc = new BarChart<String, Number>(xAchse, yAchse);
			
	ArrayList<String> Karteien = new ArrayList<String>();
	ArrayList<String> Punkte = new ArrayList<String>();
			
	Model pm = getController().getModel("profil");
	
	@Override
	public Parent constructContainer() {
		
		//HBox für das Diagramm
		Diagram.setAlignment(Pos.CENTER);
		
		//HBox für die Buttons / Controls
		Controls.setAlignment(Pos.BOTTOM_CENTER);
		
		//Buttons / Controls
		back.setOnAction(e -> {getController().showMainView();delOldStats();});
		Controls.getChildren().addAll(back);
		
		Pane.setCenter(Diagram);
		Pane.setBottom(Controls);
		
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
		yAchse.setLabel("Punkte");
		
		Karteien = pm.getDataList("karteien");
		Punkte = pm.getDataList("punkte");
		
		for (int i = 0; i < Karteien.size(); i++)
		{
			Double tempPunkte = Double.parseDouble(Punkte.get(i));
			serie.getData().add(new XYChart.Data(Karteien.get(i), tempPunkte));
		}
		
		bc.getData().addAll(serie);
		Diagram.getChildren().addAll(bc);
		} catch (Exception e) {
			Debugger.out(e.getMessage());
		}
	}
	
	private void delOldStats()
	{
		Karteien = null;
		Punkte = null;
		serie = null;
	}
}
