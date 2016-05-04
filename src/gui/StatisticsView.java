package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.FXSettings;
import mvc.FXView;

/**
 * @author nina-egger
 *
 */


public class StatisticsView extends FXView
{		
	HBox ChartLayout;
	
	//Setze die namen der balken
	final static String austria = "Austria";
	final static String brazil = "Brazil";
	final static String france = "France";
	final static String italy = "Italy";
	final static String usa = "USA";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public StatisticsView(String setName, Controller controller)
	{
		super (setName, controller);
		
		// Buttons
		AppButton zurueck = new AppButton("zurück");
		AppButton meineKlasse = new AppButton("Meine Klasse");
		AppButton rangliste = new AppButton("Ranglisten");
		
		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);

		hBox.getChildren().addAll(zurueck, meineKlasse, rangliste);

		//Buttons/Labels für Info Box (Hier werden Name und so weiter angezeigt werden (wie Profil)
		AppButton button1 = new AppButton("Button_1");
		AppButton button2 = new AppButton("Button_2");
		AppButton button3 = new AppButton("Button_3");
		AppButton button4 = new AppButton("Button_4");
		
		//InfoBox(Links vom Chart)
		VBox infoBox = new VBox(50);
		infoBox.setAlignment(Pos.CENTER_LEFT);
		infoBox.getChildren().addAll(button1, button2, button3, button4);
		
		//ChartLayout erstellen
		ChartLayout = new HBox(20);
		ChartLayout.setAlignment(Pos.CENTER);
		

		// Laayout für die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		
		borderPane.setLeft(infoBox);
		borderPane.setRight(ChartLayout);
		borderPane.setBottom(hBox);
		 
		
		//Beide Achsen erstellen
		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        //Barchart erstellen
        final BarChart<String,Number> bc = 
        new BarChart<String,Number>(xAxis,yAxis);
        
        //Titel und Label setzen
        bc.setTitle("Country Summary");
        xAxis.setLabel("Country");       
        yAxis.setLabel("Value");
 
        
        //Für die erste Balken Serie die Werte setzen
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");       
        series1.getData().add(new XYChart.Data<String, Double>(austria, 25601.34));
        series1.getData().add(new XYChart.Data<String, Double>(brazil, 20148.82));
        series1.getData().add(new XYChart.Data<String, Integer>(france, 10000));
        series1.getData().add(new XYChart.Data<String, Double>(italy, 35407.15));
        series1.getData().add(new XYChart.Data<String, Integer>(usa, 12000));      
        
        //Für die zweite Balken Serie die Werte setzen
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data<String, Double>(austria, 57401.85));
        series2.getData().add(new XYChart.Data<String, Double>(brazil, 41941.19));
        series2.getData().add(new XYChart.Data<String, Double>(france, 45263.37));
        series2.getData().add(new XYChart.Data<String, Double>(italy, 117320.16));
        series2.getData().add(new XYChart.Data<String, Double>(usa, 14845.27));  
        
        //Für die dritte Balken Serie die Werte setzen
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().add(new XYChart.Data<String, Double>(austria, 45000.65));
        series3.getData().add(new XYChart.Data<String, Double>(brazil, 44835.76));
        series3.getData().add(new XYChart.Data<String, Double>(france, 18722.18));
        series3.getData().add(new XYChart.Data<String, Double>(italy, 17557.31));
        series3.getData().add(new XYChart.Data<String, Double>(usa, 92633.68));

        //Alles zusammenfügen
        bc.getData().addAll(series1, series2, series3);
        
        
        //Chart ins KartenLayout (center) einfügen
        ChartLayout.getChildren().addAll(bc);
        
		this.setupScene(new Scene(borderPane, FXSettings.OPTIMAL_WIDTH, FXSettings.OPTIMAL_HEIGHT));
		zurueck.setOnAction(e -> controller.showMain());
		

	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}
