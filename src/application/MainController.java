package application;

import gui.HelpView;
import gui.MainView;
import gui.OptionsView;
import gui.DoorView;
import javafx.stage.Stage;


public class MainController
{
	// Alle Views
	MainView	main;
	DoorView doors;
	HelpView	help;
	OptionsView	options;

	public MainController (Stage primaryStage)
	{
		primaryStage.setTitle("WISS Learn Cards [Alpha]");

		main = new MainView(primaryStage, this);
		doors = new DoorView(primaryStage, this);
		options = new OptionsView(primaryStage, this);
		help = new HelpView();
	}

	public void showMain()
	{
		main.show();
	}
	
	public void showDoors()
	{
		doors.show();
	}

	public void showOptions ()
	{
		options.show();
	}

	public void showHelp ()
	{
		help.show();
	}

	// TODO quit method
}
