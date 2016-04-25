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
	private final String appTitle = "Auto Simulator (Java FX Demo)";
	private final ArrayList<View>	views	= new ArrayList<>();

	public MainController (Stage primaryStage)
	{
		primaryStage.setTitle("WISS Learn Cards [Alpha]");

		views.add(new MainView			( mainView, primaryStage, this) );
		views.add(new StatisticsView	("statisticsview", primaryStage, this) );
		views.add(new DoorView			("doorview", primaryStage, this) );
		views.add(new OptionsView		("optionsview", primaryStage, this) );
		views.add(new HelpView			("helpview") );
		views.add(new GameView			("gameview", primaryStage, this));
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
