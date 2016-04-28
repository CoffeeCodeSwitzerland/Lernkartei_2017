package application;

import java.util.ArrayList;

import debug.Debugger;
import debug.Supervisor;
import gui.*;
import javafx.stage.Stage;
import models.*;


/**
 * Diese Klasse Kontrolliert alle Sichten und bietet die naviagtion zur nächsten
 * Sicht an. Alle Sichten (ausser Modalfenster) werden hier mit eindeutigen
 * Namen versehen.
 * 
 * @author miro-albrecht & hugo-lucca
 *
 */
public class MainController
{

	private final String			mainView	= "mainview";
	private final ArrayList<View>	views		= new ArrayList<View>();
	private final ArrayList<Model>	models		= new ArrayList<Model>();

	public MainController (Stage primaryStage)
	{
		// Zuerst Model kreieren, dann Views!

		models.add(new GameModel("game"));
		models.add(new DoorModel("door"));

		views.add(new MainView(mainView, primaryStage, this));
		views.add(new StatisticsView("statisticsview", primaryStage, this));
		views.add(new DoorView("doorview", primaryStage, this));
		views.add(new OptionsView("optionsview", primaryStage, this));
		views.add(new HelpView("helpview"));
		views.add(new GameView("gameview", primaryStage, this));
		views.add(new KarteiView("karteiview", primaryStage, this));
		views.add(new KastenView("kastenview", primaryStage, this));
		views.add(new DragDropView("dragview", primaryStage, this));
	}

	public Model getMyModel (String name)
	{
		for (Model m : models)
		{
			Debugger.out("model found: " + m.getName());
			if (m.getName().equals(name)) { return m; }
		}

		if (name != null)
			Supervisor.warnAndDebug(this, "model(" + name + ") not found!");
		else
			Supervisor.warnAndDebug(this, "model(null) not allowed!");

		return null; // not found
	}

	public View showMain ()
	{
		return show(mainView);
	}

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
		return null; // not found
	}

	// TODO quit method
}
