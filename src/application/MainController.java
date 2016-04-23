package application;

import gui.*;

import java.util.ArrayList;

import javafx.stage.Stage;


public class MainController
{
	ArrayList<View>	views	= new ArrayList<>();

	public MainController (Stage primaryStage)
	{
		primaryStage.setTitle("WISS Learn Cards [Alpha]");

		views.add(new MainView(primaryStage, this));
		views.add(new StatisticsView(primaryStage, this));
		views.add(new DoorView(primaryStage, this));
		views.add(new OptionsView(primaryStage, this));
		views.add(new HelpView());
	}

	public void show (String name)
	{
		for (View v : views)
		{
			if (v.getName().equals(name))
			{
				v.show();
				return;
			}
		}
	}

	// TODO quit method
}
