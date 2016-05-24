package views;

import debug.Debugger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import models.StatisticsModel;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
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
		super(newController);
		construct(newName);
	}
	
	private HBox Diagram;
	private HBox Controls;
	private HBox Rankings;
	final AppButton back = new AppButton("_Zurück");
	final BorderPane Pane = new BorderPane();
	//Achsen erstellen
	final CategoryAxis xAchse = new CategoryAxis();
	final NumberAxis yAchse = new NumberAxis();

	private StatisticsModel sm;
	
	private int countLoadsOfRefreshView = 0;
			
	@Override
	public Parent constructContainer() {
		
		Controls = new HBox(50);
		
		//HBox für die Buttons / Controls
		Controls.setAlignment(Pos.BOTTOM_CENTER);
		Controls.setPadding(new Insets(15));
		
		//Buttons / Controls
		back.setOnAction(e -> getController().showMainView());
		Controls.getChildren().addAll(back);
		
		Pane.setBottom(Controls);
		
		countLoadsOfRefreshView += 1;
		
		return Pane;
	}

	//Hier werden die Daten für das Diagramm geholt. Ich habe dafür im StatisticsModel eigene Funktionen erstellt, da ich nicht mit ArrayLists arbeiten kann. 
	//Ich Caste darum auch auf das StatisticsModel und greife nicht herkömmlich nur mit getController() darauf zu.
	@Override
	public void refreshView()
	{	
		System.out.println(countLoadsOfRefreshView);
		if (countLoadsOfRefreshView >= 1) { 
			System.out.println("Löschen einleitung");
			if (Diagram != null && Rankings != null) {
				delOld();
				countLoadsOfRefreshView += 1;
			} 
		}
		
		Diagram = new HBox(50);
		Rankings = new HBox(50);
		
		//HBox für die Rankings
		Rankings.setAlignment(Pos.CENTER_LEFT);
		Rankings.setPadding(new Insets(15));
		
		//HBox für Diagramm
		Diagram.setAlignment(Pos.CENTER);

		//BarChart erstellen
		BarChart<String, Number> bc = new BarChart<String, Number>(xAchse, yAchse);				
		bc.setAnimated(true);
		
		//ListView
		ListView<String> Ranks = new ListView<String>();
		
		//Daten für Rangliste abholen über StatisticsModel und dann Rangliste.java
		sm = ((StatisticsModel) getController().getFXModel("statistics"));
		
		xAchse.setLabel("Karteien");
		yAchse.setLabel("Ergebnis (%)");
		try {
			if (sm.getObservableDiagrammList("saulendiagramm") == null && 
					sm.getObservableDataList("Rangliste") == null) {
				//Dieses TextField wird geaddet, wenn keine Daten für die Rangliste oder die Rangliste gefunden werden können.
				TextField m = new TextField();
				m.setText("Es tut uns leid aber wir konnten keine Daten zur Auswertung Ihrer Statistik finden");
				Diagram.getChildren().add(m);
			} else {
				if (sm!= null) {
				Ranks.setItems(sm.getObservableDataList("Rangliste"));
				} else {
					System.out.println("Kein sm model!!!");
				}
				Rankings.getChildren().addAll(Ranks);
				
				//Daten für das Diagramm. Die verarbeitugn und bereitstellung findet alles in Diagramm.java (getChartData()) statt.
				bc.getData().addAll(sm.getObservableDiagrammList("saulendiagramm"));
				
				//Der HBox "Diagramm" das BarChart adden welches das das Säulendiagramm beinhaltet. 
				//Wird hier gemacht, weil bei Fehler andere Komponente geaddet wird (siehe weiter oben das TextField)
				Diagram.getChildren().addAll(bc);
				
				//Hier werden sämtliche Daten auch in den anderen Klassen über das Model gelöscht, damit sicher ist, dass nirgends Datenleichen herumgeistern
				sm.doAction("DeleteOldData");
			}
		} catch (Exception e) {
			Debugger.out("StatsView Exception: ");
			e.printStackTrace();
		}
		
		Pane.setCenter(Diagram);
		Pane.setLeft(Rankings);
	}
	
	private void delOld() {
		System.out.println("Löschen");
		Diagram.getChildren().clear();
		Rankings.getChildren().clear();
		Pane.getChildren().clear();
	}
}
