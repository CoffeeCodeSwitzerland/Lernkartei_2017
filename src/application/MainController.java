package application;

import java.util.ArrayList;

import debug.Supervisor;
import gui.*;
import models.*;
import javafx.stage.Stage;


/**
 * Diese Klasse Kontrolliert alle Sichten und Models. Den Sichten wird die
 * Navigation zur Verfügung gestellt. Alle Sichten (ausser Modalfenster) werden
 * hier mit eindeutigen Namen versehen.
 * 
 * @author miro albrecht & hugo-lucca
 *
 */
public class MainController
{
	private final String				mainView	= "mainview";
	private final ArrayList<View>		views		= new ArrayList<View>();
	private final ArrayList<DataModel>	dataModels	= new ArrayList<DataModel>();

	public MainController (Stage primaryStage)
	{
		// Zuerst DataModel kreieren, dann Views!
		// Einzigartiger Name (jeweils Views & Models) ist erforderlich!

		dataModels.add(new GameModel("game"));
		dataModels.add(new DoorModel("door"));
		dataModels.add(new BoxModel("box"));
		dataModels.add(new CardModel("cards"));

		views.add(new MainView(mainView, primaryStage, this));
		views.add(new StatisticsView("statisticsview", primaryStage, this));
		views.add(new DoorView("doorview", primaryStage, this));
		views.add(new OptionsView("optionsview", primaryStage, this));
		views.add(new HelpView("helpview"));
		views.add(new GameView("gameview", primaryStage, this));
		views.add(new KarteiView("karteiview", primaryStage, this));
		views.add(new BoxView("boxview", primaryStage, this));
		views.add(new EditorView("editorview", primaryStage, this));
		views.add(new ImpressumView("impressumview", primaryStage, this));
	}

	public DataModel getMyModel (String name)
	{
		for (DataModel m : dataModels)
		{
			// Debugger.out("model found: " + m.getName());
			if (m.getName().equals(name)) { return m; }
		}

		if (name != null)
			Supervisor.warnAndDebug(this, "model(" + name + ") not found!");
		else
			Supervisor.warnAndDebug(this, "model(null) not allowed!");

		// Kein Model gefunden
		return null;
	}

	/**
	 * Zeigt MainView auf dem Hauptfenster an
	 * 
	 * @return Gibt die MainView zurück
	 */
	public View showMain ()
	{
		return show(mainView);
	}

	/**
	 * Zeigt eine View auf dem Hauptfenster an
	 * 
	 * @param name
	 *            Name der View
	 * @return View, null wenn keine gefunden wurde
	 */
	public View show (String name)
	{
		for (View v : views)
		{
			if (v.getName().equals(name))
			{
				v.show();
				return v;
			}
		}

		if (name != null)
			Supervisor.warnAndDebug(this, "show(" + name + ") not found!");
		else
			Supervisor.warnAndDebug(this, "show(null) not allowed!");

		// Keine View gefunden
		return null;
	}
}
