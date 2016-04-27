package application;

import java.util.ArrayList;

import debug.Debugger;
import debug.Supervisor;
import gui.DoorView;
import gui.GameView;
import gui.HelpView;
import gui.MainView;
import gui.OptionsView;
import gui.StatisticsView;
import gui.View;
import javafx.stage.Stage;
import models.GameModel;
import models.Model;

/**
 * Main Controller
 * 
 * @author miro-albrecht & hugo-lucca
 *
 */
public class MainController
{
	/**
	 * Diese Klasse Kontrolliert alle Sichten und bietet die naviagtion zur nächsten Sicht an.
	 * Alle Sichten (ausser Modalfenster) werden hier mit eindeutigen Namen versehen.
	 */
	private final String mainView  = "mainview";
	private final ArrayList<View>	views	= new ArrayList<View>();
	private final ArrayList<Model>	models	= new ArrayList<Model>();

	//private final ModelInterface myModel = null; // Lernkartei

	public MainController (Stage primaryStage)
	{
		views.add(new MainView			( mainView, primaryStage, this) );
		views.add(new StatisticsView	("statisticsview", primaryStage, this) );
		views.add(new DoorView			("doorview", primaryStage, this) );
		views.add(new OptionsView		("optionsview", primaryStage, this) );
		views.add(new HelpView			("helpview") );
		views.add(new GameView			("gameview", primaryStage, this));
		
		models.add(new GameModel("game"));
	}

	public ModelInterface getMyModel(String name) {
		//return myModel;
		for (Model m : models)
		{
			Debugger.out("model found: "+m.getName());
			if (m.getName().equals(name))
			{
				return m;
			}
		}
		if (name != null)
			Supervisor.warnAndDebug(this, "model("+name+") not found!");
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
			Supervisor.warnAndDebug(this, "show("+name+") not found!");
		else
			Supervisor.warnAndDebug(this, "show(null) not allowed!");
		return null; // not found
	}

	// TODO quit method
}
