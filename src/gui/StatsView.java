package gui;

import java.util.ArrayList;

import application.Constants;
import application.MainController;
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
 * @Joel Du hast Skizzen dazu gemacht um schneller zu entwickeln
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
	final CategoryAxis xAchse;
	final NumberAxis yAchse;
	//Ein Bar Chart um Diagramm anzeigen zu können
	private final BarChart<String, Number> stats;
	//Box für das Diagramm
	private HBox ChartLayout;
	
	
	public StatsView(String setName, MainController controller)
	{
		super(setName, controller);
		xAchse = new CategoryAxis();
		yAchse = new NumberAxis();
		stats = new BarChart<String, Number>(xAchse, yAchse);
		generateTwoArrays();
		generateSerie();
		
		//ChartLayout erstellen
		ChartLayout = new HBox(20);
		ChartLayout.setAlignment(Pos.CENTER);
		
		// Buttons
		AppButton zurueck = new AppButton("zurück");
		
		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(zurueck);
		
		//Titel und Label setzen
        stats.setTitle("Statistik der Karteien");
        xAchse.setLabel("Name");       
        yAchse.setLabel("Punkte");
        
        //Chart ins KartenLayout (center) einfügen
        ChartLayout.getChildren().addAll(stats);
        
        //zurueck eine Action hinzufügen
        zurueck.setOnAction(e -> controller.showMain());
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
	public void show()
	{
		BorderPane borderPane = new BorderPane();
		this.setupScene(new Scene(borderPane , FXSettings.OPTIMAL_WIDTH, FXSettings.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView()
	{
		getController().getModel("").registerView(this);
	}
	
}
