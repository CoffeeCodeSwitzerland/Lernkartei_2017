package views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.HBox;
import models.ProfilModel;
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
	public StatsView(String newName) {
		// this constructor is the same for all view's on same stage
		super(newName);
		Parent p = constructContainer();
		if (p==null) {
			p = getMainLayout();
		}
		p.setId(this.getName());
		setupScene(p);
	}

	//Zugehöriges Model deklarieren und instanzieren
	ProfilModel Pm = new ProfilModel("heee?");
	
	//Arrays für Daten welche ich von ProfilModel erhalte
//	private ArrayList<String> Karteien;
//	private ArrayList<String> Punkte;
	
	
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })

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
		
		//Eine Serie erstellen
//		Series serie = new Series();
//		for (int i = 0; i < Karteien.size(); i++)
//		{
//			serie.getData().add(new XYChart.Data(Karteien.get(i), Punkte.get(i)));
//		}
		
//		bc.getData().addAll(serie);
		
		//Szene setzen und Buttons Event
		back.setOnAction(e -> getController().getMainViewName());
		return bc;
	}

	@Override
	public void refreshView()
	{
	}
}
