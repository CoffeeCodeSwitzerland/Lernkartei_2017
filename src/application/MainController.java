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
	private final ArrayList<DataModel>	dataModels		= new ArrayList<DataModel>();
	private View					currentView	= null;

	public MainController (Stage primaryStage)
	{
		// Zuerst DataModel kreieren, dann Views!

		dataModels.add(new GameModel("game"));
		dataModels.add(new DoorModel("door"));
		dataModels.add(new BoxModel("box"));

		views.add(new MainView(mainView, primaryStage, this));
		views.add(new StatisticsView("statisticsview", primaryStage, this));
		views.add(new DoorView("doorview", primaryStage, this));
		views.add(new OptionsView("optionsview", primaryStage, this));
		views.add(new HelpView("helpview"));
		views.add(new GameView("gameview", primaryStage, this));
		views.add(new KarteiView("karteiview", primaryStage, this));
		views.add(new BoxView("boxview", primaryStage, this));
		views.add(new DragDropView("dragview", primaryStage, this));
	}

	public DataModel getMyModel (String name)
	{
		for (DataModel m : dataModels)
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
		View v = show(mainView);
		currentView = v;
		return v;
	}

	public View show (String name)
	{
		for (View v : views)
		{
			if (v.getName().equals(name))
			{
				v.show();
				currentView = v;
				return v;
			}
		}
		if (name != null)
			Supervisor.warnAndDebug(this, "show(" + name + ") not found!");
		else
			Supervisor.warnAndDebug(this, "show(null) not allowed!");
		return null; // not found
	}

	public View getCurrent ()
	{
		return currentView;
	}
}
