package gui;

import java.util.ArrayList;

import application.Constants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import mvc.*;
/**
 * Diese Klasse soll die gleiche Funktionalität wie StatisticsView haben und diese dann auch ersetzen
 * Sie soll beliebig viele Säulen generieren
 * 
 * @author Joel Häberli
 *
 */

public class StatsView extends FXView
{
	//Alle Daten Holen
	private ArrayList<String> OriginalData = new ArrayList<String>();
	
	//Alle Daten in zwei Arrays einteilen: Kartei[] und Points[]
	private String[] Kartei = new String[OriginalData.size()];
	private Double[] Points = new Double[OriginalData.size()];
	
	//X und Y Achsen erstellen
	final CategoryAxis xAchse = new CategoryAxis();
	final NumberAxis yAchse = new NumberAxis();
	
	//Ein Bar Chart um Diagramm anzeigen zu können
	private final BarChart<String, Number> stats;
	

	public StatsView(String setName, Controller controller)
	{
		super(setName, controller);

		//Chart erstellen
		stats = new BarChart<String, Number>(xAchse, yAchse);
		//Titel und Label setzen
        stats.setTitle("Statistik der Karteien");
        xAchse.setLabel("Name");       
        yAchse.setLabel("Punkte");

//        generateTwoArrays();
//		generateSerie();

		//Box für das Diagramm
		HBox chartLayout = new HBox(50);
		chartLayout.setAlignment(Pos.CENTER);
        //Chart ins KartenLayout (center) einfügen

		// Buttons
		AppButton zurueck = new AppButton("zurück");
		// zurueck eine Action hinzufügen
        zurueck.setOnAction(e -> getController().showMain());
		
		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(zurueck);
		
		BorderPane bp = new BorderPane();
		bp.getChildren().addAll(chartLayout, hBox, stats);

		setupScene(new Scene(bp, FXSettings.OPTIMAL_WIDTH, FXSettings.OPTIMAL_HEIGHT));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void generateSerie() 
	{
		//Eine Serie für jede Kartei erstellen
		for (int i = 0; i < Points.length; i++)
		{
			XYChart.Series serie = new XYChart.Series<>();
			serie.setName(Kartei[i]);
			serie.getData().add(new XYChart.Data(Kartei[i], Points[i]));
			stats.getData().addAll(serie);
		}
	}
	
	public void generateTwoArrays() 
	{
		//Aus den Originaldaten zwei Arrays erstellen
		for (int i = 0; i < OriginalData.size(); i++)
		{
			String[] temp = new String[2];
			temp = OriginalData.get(i).split(Constants.SEPARATOR);
			Kartei[i] = temp[0];
			Points[i] = Double.parseDouble(temp[1]);
		}
	}

	@Override
	public void refreshView()
	{
		
	}
	
}
