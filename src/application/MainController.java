package application;

import gui.*;

import java.util.ArrayList;

import javax.swing.JFrame;

import javafx.stage.Stage;


public class MainController
{
	private final ArrayList<View>	views	= new ArrayList<>();


	public MainController (Stage primaryStage)
	{
		primaryStage.setTitle("WISS Learn Cards [Alpha]");

		views.add(new MainView("mainview", primaryStage, this));
		views.add(new StatisticsView("statisticsview", primaryStage, this));
		views.add(new DoorView("doorview", primaryStage, this));
		views.add(new OptionsView("optionsview", primaryStage, this));
		views.add(new HelpView("helpview"));
		views.add(new GameView("gameview", primaryStage, this));
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
		return null;
	}

	// TODO quit method
}
