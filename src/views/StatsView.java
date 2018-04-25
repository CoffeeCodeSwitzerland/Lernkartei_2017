package views;

import debug.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import models.StatisticsModel;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;

/**
 * Diese Klasse soll die gleiche Funktionalit�t wie StatisticsView haben und
 * diese dann auch ersetzen Sie soll beliebig viele S�ulen generieren
 * 
 * @author Joel H�berli
 *
 */

public class StatsView extends FXView
{
	public StatsView(String newName, FXController newController)
	{
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	private StatisticsModel statisticsModel;

	// ListView
	final ListView<String> Ranks = new ListView<String>();

	final BorderPane pane = new BorderPane();
	final AppButton back = new AppButton("Zur�ck");
	final AppButton learn = new AppButton("Learn");
	// Achsen erstellen
	final CategoryAxis xAchse = new CategoryAxis();
	final NumberAxis yAchse = new NumberAxis();

	final HBox diagram = new HBox(50);
	final HBox controls = new HBox(50);
	final HBox rankings = new HBox(50);

	final HBox error = new HBox(50);

	final Label errorText = new Label();

	// private int countLoadsOfRefreshView = 0;

	@Override
	public Parent constructContainer()
	{

		// HBox f�r die Buttons / Controls
		controls.setAlignment(Pos.BOTTOM_CENTER);
		controls.setPadding(new Insets(15));

		// Buttons / Controls
		back.setOnAction(e -> getFXController().showMainView());
		controls.getChildren().addAll(back, learn);

		pane.setBottom(controls);

		// HBox f�r die Rankings
		rankings.setAlignment(Pos.CENTER_LEFT);
		rankings.setPadding(new Insets(15));

		// HBox f�r Diagramm
		diagram.setAlignment(Pos.CENTER);

		// HBox f�r Errors oder Meldungen -> Wird nicht Visible gesetzt.
		error.setPadding(new Insets(15));
		error.setAlignment(Pos.TOP_CENTER);
		error.getChildren().addAll(errorText);
		pane.setTop(error);

		return pane;
	}

	// Zeigt das Prelearnfenster des Stacks an, welchen man ausgew�hlt hat. Wenn
	// nichts gew�hlt ist geht es auf den Doorview.
	private void learnThisStack()
	{
		String choice = Ranks.getSelectionModel().getSelectedItem();
		if (choice == "" || choice == null)
		{
			getFXController().showAndTrackView("doorview");
		} else
		{
			String[] splittetChoice = choice.split("     ");
			getFXController().setViewData("prelearn", splittetChoice[0]);
			getFXController().showAndTrackView("prelearn");
		}
	}

	// Hier werden die Daten f�r das Diagramm geholt. Ich habe daf�r im
	// StatisticsModel eigene Funktionen erstellt, da ich nicht mit ArrayLists
	// arbeiten kann.
	// Ich Caste darum auch auf das StatisticsModel und greife nicht herk�mmlich
	// nur mit getController() darauf zu.
	@Override
	public void refreshView()
	{
		learn.setOnAction(e -> learnThisStack());

		Ranks.getItems().clear();
		Ranks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		diagram.getChildren().clear();
		rankings.getChildren().clear();

		// BarChart erstellen
		BarChart<String, Number> bc = new BarChart<String, Number>(xAchse, yAchse);
		bc.setAnimated(true);

		// Daten f�r Rangliste abholen �ber StatisticsModel und dann
		// Rangliste.java
		try {
			statisticsModel = ((StatisticsModel) getFXController().getFXModel("statistics"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		xAchse.setLabel("Karteien");
		yAchse.setLabel("Ergebnis (%)");
		
		try
		{
				statisticsModel.doAction("DeleteOldData");
				if (statisticsModel != null)
				{
					if (statisticsModel.getObservableDataList("Rangliste").get(0).equals("thisIsEmpty"))
					{
						errorText.setText("Das Programm konnte die Daten f�r die Rangliste oder das Diagramm nicht laden");
					} else
					{
						Ranks.setItems(statisticsModel.getObservableDataList("Rangliste"));
						// Daten f�r das Diagramm. Die verarbeitugn und
						// bereitstellung
						// findet alles in Diagramm.java (getChartData()) statt.
						bc.getData().addAll(statisticsModel.getObservableDiagrammList("saulendiagramm"));
					}
				} else
				{
					Logger.out("Kein sm model!!!");
				}
				rankings.getChildren().addAll(Ranks);

				// Der HBox "Diagramm" das BarChart adden welches das das
				// S�ulendiagramm beinhaltet.
				// Wird hier gemacht, weil bei Fehler andere Komponente geaddet
				// wird (siehe weiter oben das TextField)
				diagram.getChildren().addAll(bc);

				// Hier werden s�mtliche Daten auch in den anderen Klassen �ber
				// das Model gel�scht, damit sicher ist, dass nirgends
				// Datenleichen herumgeistern
				statisticsModel.doAction("DeleteOldData");

				// Wenn kein Error passiert kann man hier noch einen Text
				// eingeben der dem User angezeigt wird-
				errorText.setText("Deine Statistik. Lerne weiterhin fleissig um 100% zu erhalten. W�hle Links die Kartei, die du gerne lernen m�chtest");
		} catch (Exception e)
		{
			Logger.out(e);
		}

		pane.setCenter(diagram);
		pane.setLeft(rankings);
	}
}
